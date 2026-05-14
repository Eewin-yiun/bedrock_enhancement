import zipfile, os, sys

jar = r'C:\Users\Administrator\.gradle\caches\forge_gradle\minecraft_repo\versions\1.20.1\client.jar'
log = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement\scan_log.txt'

with open(log, 'w', encoding='utf-8') as f:
    with zipfile.ZipFile(jar, 'r') as z:
        names = z.namelist()
        f.write(f'Total entries: {len(names)}\n\n')
        
        # Search for humanoid equipment (1.20+ format)
        humanoid = [n for n in names if 'humanoid' in n and n.endswith('.png')]
        f.write(f'Humanoid equipment PNGs ({len(humanoid)}):\n')
        for p in humanoid:
            f.write(f'  {p}\n')
        f.write('\n')
        
        # Search for models/armor path
        models_armor = [n for n in names if 'models' in n and 'armor' in n.lower()]
        f.write(f'models + armor entries ({len(models_armor)}):\n')
        for p in models_armor:
            f.write(f'  {p}\n')
        f.write('\n')
        
        # Search for any PNG with 'layer'
        layer = [n for n in names if 'layer' in n.lower() and n.endswith('.png')]
        f.write(f'PNGs with layer ({len(layer)}):\n')
        for p in layer:
            f.write(f'  {p}\n')
        f.write('\n')
        
        # List all unique directory prefixes for textures
        f.write('All unique top-level asset directories with PNGs:\n')
        dirs = set()
        for n in names:
            if n.endswith('.png'):
                parts = n.split('/')
                if len(parts) >= 3:
                    dirs.add('/'.join(parts[:3]))
        for d in sorted(dirs):
            f.write(f'  {d}/\n')
