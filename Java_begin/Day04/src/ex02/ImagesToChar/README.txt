1. Команда создает автоматически папку target в корне проекта и подпапки в ней. Компилирует файлы, используя две библиотеки в конечную папку target/edu/school21/printer

javac -cp lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar:lib/JColor-5.5.1.jar src/java/edu/school21/printer/*/*.java -d target

2. Команда копирует папку resources и подпапки по адресу target/resources

cp -r src/resources target/resources

3. Перейти в папку target

cd target

4. Разархивировать jar файлы в папку target, архивация произойдёт только папок с названием com.

jar xf ../lib/JCDP-4.0.2.jar com
jar xf ../lib/jcommander-1.82.jar com

5. Программой jar создастся  файл images-to-chars-printer.jar при помощи файла manifest.txt, выводится подробная информация процесса создания и сбор файлов из папки target

jar cvfm images-to-chars-printer.jar ../src/manifest.txt ../target .

6. Запуск программы из jar архива с добавлением двух аргументов

java -jar images-to-chars-printer.jar --white=RED --black=GREEN