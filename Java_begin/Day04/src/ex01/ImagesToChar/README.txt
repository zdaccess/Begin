1. Команда создает автоматически папку target в корне проекта и подпапки в ней. Компилирует файлы в конечную папку target/edu/school21/printer

javac -d target src/java/edu/school21/printer/*/*.java

2. Команда копирует папку resources и подпапки по адресу target/resources

cp -r src/resources target/resources

3. Создается новый файл при помощи файла manifest.txt, выводится подробная информация процесса создания и сбор файлов из папки target

jar cvfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

4. Запуск программы из jar архива с добавлением двух аргументов

java -jar target/images-to-chars-printer.jar . 0