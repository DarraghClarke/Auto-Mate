# Auto-Mate
Auto-mate is a automation tool that takes in JSON formatted files and runs a series of (mostly) HTTP commands based on them

## Running Example
Currently, to run the below sample, simply build the program and run it with an argument that points to the location of  `ExampleInput.txt`

## Example
The below block features most of the existing features of Auto-Mate, 
 - A `HTTPRequestAction` action named `location` that takes in a url as it's option, this makes a call to the provided url and stores the response
 - Following this another request is made named `sunset` this takes two specific fields from `location`, namely `location.latitude` and `location.longtitude`, the values get subtituted into the string and another request is made and it's response stored
- A `GateAction` then takes place, this essentially acts as an if check, if `sunset.status` gave the expected value (in this case `"OK"`) then the code will continue, else it will stop here"
- Finally a `PrintAction` takes place, for our example it prints out the time that sunset occurs in the users location
```
{
  "actions": [
    {
      "type": "HTTPRequestAction",
      "name": "location",
      "options": {
        "url": "http://free.ipwhois.io/json/"
      }
    },
    {
      "type": "HTTPRequestAction",
      "name": "sunset",
      "options": {
        "url": "https://api.sunrise-sunset.org/json?lat={{location.latitude}}&lng={{location.longitude}}"
      }
    },
    {
      "type": "GateAction",
      "name": "gate",
      "options": {
        "value": "{{sunset.status}}",
	      "expected": "OK"
      }
    }
    {
      "type": "PrintAction",
      "name": "print",
      "options": {
        "message": "Sunset in {{location.city}}, {{location.country}} is at {{sunset.results.sunset}}"
      }
    }
  ]
}
```


## Currently Supported Types
  - HTTPRequestAction
  - PrintAction
  - GateAction

In theory it could support emails, which would act essentially like print actions, but sending the output to a specific email address, though this would require the infrastructure needed to send emails which is currently not implemented
