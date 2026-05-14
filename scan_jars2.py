import zipfile, os

jar = r'C:\Users\Administrator\.gradle\caches\forge_gradle\minecraft_repo\versions\1.20.1\client.jar'
out = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\jar_debug2.txt'

with open(out, 'w') as f:
    with zipfile.ZipFile(jar, 'r') as z:
        names = z.namelist()
        f.write(f'Total entries: {len(names)}\n\n')
        
        # Search for anything with 'models' and 'armor'
        model_armor = [n for n in names if 'models' in n and 'armor' in n]
        f.write(f'models + armor entries: {len(model_armor)}\n')
        for m in model_armor:
            f.write(f'  {m}\n')
        f.write('\n')
        
        # Search for netherite + layer
        layer = [n for n in names if 'layer' in n.lower() and 'netherite' in n.lower()]
        f.write(f'netherite + layer entries: {len(layer)}\n')
        for l in layer:
            f.write(f'  {l}\n')
        f.write('\n')
        
        # All netherite entries
        netherite = [n for n in names if 'netherite' in n.lower()]
        f.write(f'All netherite entries ({len(netherite)}):\n')
        for n in netherite:
            f.write(f'  {n}\n')
