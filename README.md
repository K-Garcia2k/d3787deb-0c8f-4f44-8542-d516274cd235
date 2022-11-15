
# KG - Demo Application

flickr_api_key="#key" required to be added in gradle.properties for build.

For this application, I focused on the design principles of MVVM. I chose this for its nature as an industry standard and for the benefits it brings. Allowing for the separation of the business logic from the views, making Unit Testing far easier, as well as allowing for better scalability and changes much nicer. With the design pattern's focus on observables, I opted to use PagingSource for a long 'infinite' scrolling list for quick and seamless data supplies combined with the PagingSourceAdapter along with LiveData.


## UI Design

For the UI Design, I opted for simple elements with material colours, choosing to show a grid 2 columns wide to test the efficiency of the adapter and UI drawing while also taking advantage of the different URL styles that FLICKR's API provides for minimizing load time. I decided on a gradient cutting into the image to condense the amount of screen real estate that each element took. I chose a minimalist design on the full details page along with a large format URL also provided by FLICKR to avoid any unnecessary cropping and image processing.

## Features

- Grid of images taken from FLICKR API
- Tags present in grid
- User Photo on Grid View
- Detail screen
- User related photos on click
- Search of tags

The features added to the application are to give the user a presentation of a list defaulted to "Red+Pandas" for the tags, giving a colourful contrast to the green immediately, allowing for the tags, user image and username to be seen accompanying the image. Clicking the image will take the user to a detail screen where they can see a larger view with more details. Including the photo description, which parses any HTML for styling that the user has added, the date of upload, and a more verbose set of tags.

On the main page, the user can click the user's buddy icon to get a full list of photos uploaded by this user. If the user wishes to search for any tags, they can then use the search bar in the header to search for one or many tags which allow for 'any' combination instead of 'all'.

## Features for future consideration

- Switching between any/all tags
- Searching photos by user
- Choose different commons licensing
- Implement download feature for images
- Sort data list

There was a list of features that did not make it into the implementation of the application demo. One of these was the ability to switch between allowing their search to have any/all tags. I would have implemented this using the options menu for a radio menu or checkbox menu. For searching the user's name I'd have implemented this by using the search query to check against the people.findByUserName, and if any results were received I would have opted to use their user ID in the photos.search query

Other features I would have liked to have added would be the ability to toggle between the different commons licensing for the photos, combining it with the ability to download the larger images from the detail page using the highest resolution URL provided by the FLICKR API.

Lastly, I would have liked to have given the user control over the way their data is sorted by giving them a list of options such as the date of upload, the title, username. Using the OptionsMenu to give these options to the user.


## SDKS

- [Retrofit](https://square.github.io/retrofit/)
- [Glide](https://github.com/bumptech/glide)
- [GreenRobot EventBus](https://greenrobot.org/eventbus/)

For this project, I opted to use three third-party libraries. One of these was Retrofit for handling the API calls, this library has one of the best supports for Kotlin its use of coroutines, and interacts well with DaggerHilt for injection and unit testing. The next SDK I used was Glide, I prefer this SDK over its competitor of Picasso due to the customisation options that are available for efficiency as well as the image processing and animation options it provides.

The last SDK I used was for a single interaction within the application which is GreenRobot's Eventbus, I like to use this SDK where applicable to avoid having to write boilerplate interfaces and callbacks. Allowing for finer control over user inputs. I used this SDK for handling a nested view click of the user's buddyicon to keep the view click functionality to send the user to the description fragment.

