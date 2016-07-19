# Pre-work - *TODOAPP*

TODOApp is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Orville Clarke

Time spent: **X** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!

I experimented with some animation.  I make the calendar and clock heading inside a clickable linearlayout, which controlled the linearlayout holding the calendar or clock (respectively) appear or disappear.  I also played with the toolbar and input some drawable icons for some editing functions for save, delete and cancel. 
## Video Walkthrough 
http://i.imgur.com/qiyC0si.gif?1


Here's a walkthrough of implemented user stories:


## Notes

Describe any challenges encountered while building the app.

I had experience mainly in C# and CSS using the Microsoft’s Visual Studios platform and some web development.  
* To begin with I had to become comfortable with Java, Android platform and come to grips with the workflow and dataflow on a cell phone.  
* One of the biggest problems was understanding and implementing fragments.  I spent many hours experimenting with examples and sometimes with my project which caused a lot of confusion later figuring out what layouts were actually part of the project (that was a true learning experience).  
* Developing thorough and concise namespace to reduce confusion.
* Working with the emulator was interesting especially after getting locked out a few times.  
* Another area was the setting up the listeners to provide the functionality.  
* But I found one of the trickiest areas was handling dates, saving and reading from the database.  I realized that the majority of the database should be created, seeded and tested before even building your UI elements.  
* UI mockup should be in place with a supporting wireframe model to help eliminate confusion and speed up UI manufacturing.   
* Writing clean code from the beginning and using GITHUB religiously.
* GITHUB, I have never used it before but I don’t know how I can live without it

## License

    Copyright [2016] [ApexBugFinder]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

