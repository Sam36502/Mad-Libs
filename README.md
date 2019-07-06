# Mad-Libs
A program for taking a specific file format and prompting the user for inputs. Displays the full Story when it's done.

## Usage:
To use the Mad-Libs console application, you just need to run the jar file from the console.
Once the program has started, you can select what folder to search for Mad-Libs files. These files
follow a specific format (see below), but are also quite simple. If it finds any of these files, it will
list all of the available files. Once you choose an option from the list, so long as the file is valid,
the application will start asking you questions and filling in the Story with your answers.

Have Fun!

## File format
If you want to make your own Mad-Libs, the simplest way is to just create a new text document
that starts with `#!format=madlibs`. After that you can write your story. Every time you want to
substitute a piece of the story with an answer from the user, you put the question they should answer
in curly brackets `{}`. Here's an example of a very simple mad-lib:
```
#!format=madlibs

Hello there {your name}!
```

## Additional Attributes
You also have the oportunity to add some extra attributes to your file such as:

### Prefix
With `#!prefix=` you can tell the application to add something to the beginning of
every question. E.g.
```
#!format=madlibs
#!prefix=the name of

Hello there {a friend}, have you seen {your grandma}?
```
This will make all the questions:
"Enter the name of a friend" and "Enter the name of your grandma"

### Author
If you're particularly proud of your creation, you can sign it with your name.
Using the `#!author=` attribute. When the Mad-Lib is used, it will say "by Your Name"
at the beginning.

### Title
Another useful attribute is the title. If no title is set then the filename is used
at the beginning of the Mad-Lib. But "madlib3.txt" is not always the best title, so
using `#!title=` you can tell the application what your story is called.
