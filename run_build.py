import subprocess
import sys
import os

os.chdir(r"C:\Users\Administrator\WorkBuddy\20260501080626\bedrock_enhancement")

result = subprocess.run(
    ["cmd.exe", "/c", "gradlew.bat", "build"],
    capture_output=True,
    text=True,
    timeout=600
)

print("EXIT CODE:", result.returncode)
print("=== STDOUT ===")
print(result.stdout)
print("=== STDERR ===")
print(result.stderr)
