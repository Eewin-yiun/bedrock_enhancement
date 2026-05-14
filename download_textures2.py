import urllib.request, os, sys

log = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\download_log2.txt'
out_dir = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\src\main\resources\assets\bedrock_enhancement\textures\models\armor'
os.makedirs(out_dir, exist_ok=True)

urls = {
    'bedrock_layer_1.png': 'https://assets.mcasset.cloud/1.20.1/assets/minecraft/textures/models/armor/netherite_layer_1.png',
    'bedrock_layer_2.png': 'https://assets.mcasset.cloud/1.20.1/assets/minecraft/textures/models/armor/netherite_layer_2.png',
}

# Fallback URLs
fallbacks = {
    'bedrock_layer_1.png': 'https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/1.20.1/assets/minecraft/textures/models/armor/netherite_layer_1.png',
    'bedrock_layer_2.png': 'https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/1.20.1/assets/minecraft/textures/models/armor/netherite_layer_2.png',
}

results = []

for fname, url in urls.items():
    out_path = os.path.join(out_dir, fname)
    success = False
    for attempt_url in [url, fallbacks[fname]]:
        try:
            results.append(f'Trying: {attempt_url}')
            req = urllib.request.Request(attempt_url, headers={'User-Agent': 'Mozilla/5.0'})
            with urllib.request.urlopen(req, timeout=15) as r:
                data = r.read()
                # Check if it's actually a PNG (starts with PNG signature)
                if data[:4] == b'\x89PNG':
                    with open(out_path, 'wb') as w:
                        w.write(data)
                    results.append(f'  OK! Saved {fname}: {len(data)} bytes (valid PNG)')
                    success = True
                    break
                else:
                    results.append(f'  FAIL: Not PNG (starts with {data[:20]})')
        except Exception as e:
            results.append(f'  FAIL: {e}')
    if not success:
        results.append(f'  ERROR: Could not download valid PNG for {fname}')

with open(log, 'w', encoding='utf-8') as f:
    f.write('\n'.join(results))
