window.onload = init;
var http = new XMLHttpRequest()
var map;
var infoWindow;
var markers = new Array();
var numMarkers;
var jobsFilter;
var jobs = new Array();
var initd = false;

function init(){
    //Gets a filter object
    jobsFilter = JSON.parse(getCookie("filter"));
    if (jobsFilter == null){ //Make new one if there is no existing one
        jobsFilter = new JobSearchFilter(true, true, true, true, true, true);
    } 
    
    //Setup the map
    resizeMapDiv()
    window.onresize = resizeMapDiv;
    
    //Send request to server to get json object of jobs
    http.open("GET", "http://142.132.145.50/3909-001-006/Controller?action=json", true);
    http.onreadystatechange = handleHttpResponse;
    http.send(null);
    
    //Create an info window for popups when markers are clicked
    infoWindow = new google.maps.InfoWindow;
    
    //Everything is initialized
    initd = true;
}

//Method called when the JSON object is retrieved
function handleHttpResponse() {
    jobs = JSON.parse(http.response); //Recreates the job from the JSON object and stores in array
    setMarkers(jobsFilter); //Set the markers on the map
}

//Method called when a check box is clicked
function handleCheckBoxClick(jobType){
    //Switch the boolean value for the clicked job type
    jobsFilter[jobType] = !jobsFilter[jobType];
    //Set the filter in the cookie
    setCookie("filter", JSON.stringify(jobsFilter), 30);
    //Remove all markers from the map
    clearMarkers();
    //Reset all markers with new filter
    setMarkers(jobsFilter);
}


//Method to set a cookied
function setCookie(c_name,value,exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = encodeURIComponent(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
    document.cookie=c_name + "=" + c_value;
}
//Method to retrieve a cookie
function getCookie(c_name)
{
    var i,x,y,ARRcookies=document.cookie.split(";");
    for (i=0;i<ARRcookies.length;i++)
    {
        x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
        y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
        x=x.replace(/^\s+|\s+$/g,"");
        if (x==c_name)
        {
            return decodeURIComponent(y);
        }
    }
    return null;
}

//Sets the markers on a map based on the filter
function setMarkers(filter){
    if(filter.P == true){
        //Retrieve specific job array from jobs object
        var pJobs = jobs.programming;
        //Plot the jobs on the map
        plotJobs(pJobs, "Programming");
    }
    if(filter.M == true){
        var mJobs = jobs.media;
        plotJobs(mJobs, "Media");
    }
    if(filter.R == true){
        var rJobs = jobs.retail;
        plotJobs(rJobs, "Retail");
    }
    if(filter.A == true){
        var aJobs = jobs.admin;
        plotJobs(aJobs, "Admin");
    }
    if(filter.D == true){
        var dJobs = jobs.design;
        plotJobs(dJobs, "Design");
    }
    if(filter.F == true){
        var fJobs = jobs.foodService;
        plotJobs(fJobs, "Food Service");
    }
}


//Remove the markers from the map
function clearMarkers() {
    for (var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
}

//Adds the jobs to the map
function plotJobs(jobCategory, category){
    for(var i = 0; i < jobCategory.length; i++){
        var myLatlng = new google.maps.LatLng(jobCategory[i].lat,jobCategory[i].lng);
        //Create new marker
        markers[numMarkers] = new google.maps.Marker({
            position: myLatlng,
            map: map,
            title: jobCategory[i].title
        }); 
        //Create the content within the infoWindow
        var contentString = '<div id="content">'+
        '<div id="siteNotice">'+
        '</div>'+
        '<h3 id="firstHeading" class="firstHeading">' + jobCategory[i].title + '</h3>'+
        '<h5>[' + category + ']</h5>' +
        '<div id="bodyContent">'+
        '<p>Posted ' + jobCategory[i].pubDate + '</p>' +
        '<a href="' + jobCategory[i].url+'">Link to Job</a>' + 
        '</div>'+
        '</div>';
        //Set the info window with a click event
        makeInfoWindowEvent(map, infoWindow, contentString, markers[numMarkers]);
        numMarkers++;
    }
}

//Adds a click event to the info window
function makeInfoWindowEvent(map, infoWindow, content, marker) {
    google.maps.event.addListener(marker, 'click', function() {
        infoWindow.setContent(content);
        infoWindow.open(map, marker);
    });
}

//A method to retrieve the style of the map
function getStyle(oElm, strCssRule){
    var strValue = "";
    if(document.defaultView && document.defaultView.getComputedStyle){
        strValue = document.defaultView.getComputedStyle(oElm, "").getPropertyValue(strCssRule);
    }
    else if(oElm.currentStyle){
        strCssRule = strCssRule.replace(/\-(\w)/g, function (strMatch, p1){
            return p1.toUpperCase();
        });
        strValue = oElm.currentStyle[strCssRule];
    }
    return strValue;
}
 
 //A method that resizes the div that contains the map when the window is changed
function resizeMapDiv(){
    //Get map dive
    var mapDiv = document.getElementById("mapCol");
    var sideBarDiv = document.getElementById("detailCol");
    //Sets the map dive height to the screen size
    mapDiv.style.height = "100%";
    //Get the heigh of the top and bottom divs
    var topDivHeight = parseInt(getStyle(document.getElementById("titleBar"), "height"));
    var bottomDivHeight = parseInt(getStyle(document.getElementById("lowerBar"), "height"));
    
    //set the map div to it's current size - the size of the top div and bottom div (size in between)
    mapDiv.style.height = (parseInt(getStyle(mapDiv, "height")) - topDivHeight - bottomDivHeight) + "px";
    //Pushes the div down the size of the top div to place it correctly
    mapDiv.style.margin = topDivHeight + "px 0 0 0";
    
    //Information with dealing with unimplemented side bar
    sideBarDiv.style.height = mapDiv.style.height;
    sideBarDiv.style.margin = mapDiv.style.margin;
    
    // Create an array of styles.
    var styles = [
    {
        stylers: [
        {
            hue: "#00ffe6"
        },
        {
            saturation: -20
        }
        ]
    },{
        featureType: "road",
        elementType: "geometry",
        stylers: [
        {
            lightness: 100
        },
        {
            visibility: "simplified"
        }
        ]
    },{
        featureType: "road",
        elementType: "labels",
        stylers: [
        {
            visibility: "on"
        }
        ]
    }
    ];

    // Create a new StyledMapType object, passing it the array of styles,
    // as well as the name to be displayed on the map type control.
    var styledMap = new google.maps.StyledMapType(styles,
    {
        name: "Styled Map"
    });

    // Create a map object, and include the MapTypeId to add
    // to the map type control.
    var mapOptions = {
        zoom: 11,
        center: new google.maps.LatLng(49.899654, -97.137494),
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'map_style']
        }
    };
    map = new google.maps.Map(document.getElementById('mapCol'),
        mapOptions);

    //Associate the styled map with the MapTypeId and set it to display.
    map.mapTypes.set('map_style', styledMap);
    map.setMapTypeId('map_style');
    //Reset number of markers
    numMarkers = 0;
    //If page is initialized
    if(initd){
        //Set the markers
        setMarkers(jobsFilter);
    }
}


//A filter that corresponds to the checkboxes displayed upon the page
function JobSearchFilter(P, M, R, A, D, F){
    this.P = P;
    this.M = M;
    this.R = R;
    this.A = A;
    this.D = D;
    this.F = F;
}