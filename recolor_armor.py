import subprocess, sys

# Check if Pillow is installed
try:
    from PIL import Image
    print("Pillow is already installed")
except ImportError:
    print("Pillow not found, installing...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "Pillow"])
    from PIL import Image
    print("Pillow installed successfully")

# Now load netherite armor textures and recolor them to bedrock style
import os

ref_dir = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\netherite_reference'
out_dir = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\src\main\resources\assets\bedrock_enhancement\textures\models\armor'

os.makedirs(out_dir, exist_ok=True)

# Netherite color: RGB(73, 79, 82) - dark grey-blue
# Bedrock color: RGB(26, 26, 46) - dark blue-black
# Actually, let's use a nicer bedrock color scheme:
#   Base:    RGB(40, 40, 60)   - dark blue-grey
#   Highlight: RGB(60, 60, 90) - lighter blue-grey
#   Shadow:   RGB(20, 20, 30)  - very dark

def recolor_netherite_to_bedrock(img):
    """Recolor a PIL Image from netherite colors to bedrock colors."""
    pixels = img.load()
    width, height = img.size
    
    # Netherite base color range: R=60-90, G=65-95, B=68-98
    # We'll map these to bedrock colors
    for y in range(height):
        for x in range(width):
            r, g, b, a = pixels[x, y]
            # Skip transparent pixels
            if a < 20:
                continue
            # Map netherite grey-blue to bedrock dark blue
            # Netherite: (73, 79, 82) -> Bedrock: (40, 40, 60)
            new_r = int(r * 0.55 * 0.9)  # Darken and shift to blue
            new_g = int(g * 0.55 * 0.9)
            new_b = int(b * 0.75)  # Keep more blue
            # Clamp
            new_r = max(0, min(255, new_r))
            new_g = max(0, min(255, new_g))
            new_b = max(0, min(255, new_b))
            pixels[x, y] = (new_r, new_g, new_b, a)
    
    return img

# Process layer_1 and layer_2
for fname in ['netherite_layer_1.png', 'netherite_layer_2.png']:
    src = os.path.join(ref_dir, fname)
    if not os.path.exists(src):
        print(f"Source not found: {src}")
        continue
    
    img = Image.open(src).convert('RGBA')
    print(f"Loaded {fname}: {img.size}")
    
    # Recolor
    img = recolor_netherite_to_bedrock(img)
    
    # Save as bedrock texture
    out_name = fname.replace('netherite', 'bedrock')
    out_path = os.path.join(out_dir, out_name)
    img.save(out_path)
    print(f"Saved to {out_path} ({os.path.getsize(out_path)} bytes)")

print("\nDone! Bedrock armor textures generated.")
