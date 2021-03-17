SET JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
RMDIR /S /Q .\build_output
IF EXIST .\build_output/nul (
    javac -d "build_output" *.java
) ELSE (
    MKDIR build_output
    javac -d "build_output" *.java
)
jar cmvf META-INF/MANIFEST.MF MoveMouse.jar -C build_output\ .