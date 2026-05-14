import urllib.request
import os

out_dir = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\netherite_reference'
os.makedirs(out_dir, exist_ok=True)

files = [
    'netherite_layer_1.png',
    'netherite_layer_2.png',
]

base_url = 'https://mcasset.cloud/1.20.1/assets/minecraft/textures/models/armor/'

for f in files:
    url = base_url + f
    out_path = os.path.join(out_dir, f)
    try:
        print(f'Downloading {url} ...')
        req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
        with urllib.request.urlopen(req, timeout=10) as r:
            data = r.read()
            with open(out_path, 'wb') as w:
                w.write(data)
            print(f'  Saved to {out_path} ({len(data)} bytes)')
    except Exception as e:
        print(f'  Error: {e}')

# Also try the equipment format for 1.20+
equip_files = [
    ('netherite.png', 'https://mcasset.cloud/1.20.1/assets/minecraft/textures/entity/equipment/humanoid/netherite.png'),
    ('netherite_leggings.png', 'https://mcasset.cloud/1.20.1/assets/minecraft/textures/entity/equipment/humanoid_leggings/netherite.png'),
]

for fname, url in equip_files:
    out_path = os.path.join(out_dir, fname)
    try:
        print(f'Downloading {url} ...')
        req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
        with urllib.request.urlopen(req, timeout=10) as r:
            data = r.read()
            with open(out_path, 'wb') as w:
                w.write(data)
            print(f'  Saved to {out_path} ({len(data)} bytes)')
    except Exception as e:
        print(f'  Error: {e}')

print('\nDone!')
