from fastapi import FastAPI, File, UploadFile, Form
from PIL import Image
import io
import torch

import numpy as np
import onnxruntime as ort
from huggingface_hub import hf_hub_download
import csv

# NEW FLORENCE IMPORTS
from transformers import AutoProcessor, AutoModelForCausalLM


# =========================================================
# DEVICE
# =========================================================
device = "cuda" if torch.cuda.is_available() else "cpu"
torch_dtype = torch.float16 if torch.cuda.is_available() else torch.float32


# =========================================================
# LOAD ANIME MODEL (UNCHANGED)
# =========================================================
model_path = hf_hub_download(
    repo_id="SmilingWolf/wd-v1-4-convnextv2-tagger-v2",
    filename="model.onnx"
)

labels_path = hf_hub_download(
    repo_id="SmilingWolf/wd-v1-4-convnextv2-tagger-v2",
    filename="selected_tags.csv"
)

labels = []

with open(labels_path, newline='', encoding="utf-8") as csvfile:
    reader = csv.DictReader(csvfile)

    for row in reader:
        labels.append(row["name"])


session = ort.InferenceSession(
    model_path,
    providers=["CPUExecutionProvider"],
    sess_options=ort.SessionOptions()
)

input_name = session.get_inputs()[0].name


def preprocess(image):
    image = image.convert("RGB")
    image = image.resize((448, 448))
    image = np.array(image).astype(np.float32)
    image = image[:, :, ::-1]
    image = np.expand_dims(image, axis=0)
    return image


def generate_anime_caption(image):

    inputs = preprocess(image)

    outputs = session.run(
        None,
        {input_name: inputs}
    )[0][0]

    tags = []

    for label, score in zip(labels, outputs):

        if score > 0.35:
            tags.append(
                label.replace("_", " ").lower()
            )

    caption = ", ".join(tags[:15])

    return caption


# =========================================================
# LOAD FLORENCE MODEL (REPLACES BLIP)
# =========================================================
print("Loading Florence-2...")

processor = AutoProcessor.from_pretrained(
    "microsoft/Florence-2-base",
    trust_remote_code=True
)

model = AutoModelForCausalLM.from_pretrained(
    "microsoft/Florence-2-base",
    torch_dtype=torch_dtype,
    trust_remote_code=True
).to(device)

print("Florence-2 loaded successfully.")


def generate_real_caption(image):

    prompt = "<CAPTION>"

    inputs = processor(
        text=prompt,
        images=image,
        return_tensors="pt"
    ).to(device, torch_dtype)

    generated_ids = model.generate(
        input_ids=inputs["input_ids"],
        pixel_values=inputs["pixel_values"],
        max_new_tokens=100,
        do_sample=False,
        num_beams=3
    )

    generated_text = processor.batch_decode(
        generated_ids,
        skip_special_tokens=True
    )[0]

    return generated_text.strip()


# =========================================================
# FASTAPI
# =========================================================
app = FastAPI()


@app.get("/")
def home():
    return {
        "message": "AI Server Running"
    }


@app.post("/generate")
async def generate_caption(
    file: UploadFile = File(...),
    mode: str = Form("real")
):

    image_bytes = await file.read()

    image = Image.open(
        io.BytesIO(image_bytes)
    ).convert("RGB")

    if mode == "real":

        # FLORENCE
        caption = generate_real_caption(image)

    elif mode == "anime":

        # WD TAGGER
        caption = generate_anime_caption(image)

    else:

        caption = "Invalid mode"

    return {
        "caption": caption
    }