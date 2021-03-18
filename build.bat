SET JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
RMDIR /S /Q .\build_output
MKDIR build_output
javac -sourcepath source -d build_output source/*.java
jar cmvf META-INF/MANIFEST.MF MoveMouse.jar -C build_output\ .