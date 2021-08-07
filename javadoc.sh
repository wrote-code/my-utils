# 生成javadoc，需要可以执行shell的工具
echo "开始生成";
javadoc -encoding utf-8 -subpackages com -sourcepath src/main/java -d javadoc
echo "生成结束";