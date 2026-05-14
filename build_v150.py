import subprocess, os, shutil, glob

proj = r'C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement'
os.chdir(proj)

# Run gradlew build
print('Running gradlew build...')
result = subprocess.run(
    ['cmd', '/c', 'gradlew.bat', 'build'],
    capture_output=True, text=True, timeout=600
)
print('STDOUT:', result.stdout[-2000:] if len(result.stdout) > 2000 else result.stdout)
print('STDERR:', result.stderr[-2000:] if len(result.stderr) > 2000 else result.stderr)
print('Return code:', result.returncode)

if result.returncode == 0:
    # Find the built jar
    libs_dir = os.path.join(proj, 'build', 'libs')
    jars = glob.glob(os.path.join(libs_dir, '*.jar'))
    for j in jars:
        size = os.path.getsize(j)
        print(f'  Built: {os.path.basename(j)} ({size} bytes)')
    
    # Copy to workspace root
    root = r'C:\Users\Administrator\WorkBuddy\20260501080626'
    for j in jars:
        if 'souces' not in j.lower() and 'dev' not in j.lower():
            dst = os.path.join(root, os.path.basename(j))
            shutil.copy2(j, dst)
            print(f'  Copied to: {dst}')
else:
    print('BUILD FAILED')
