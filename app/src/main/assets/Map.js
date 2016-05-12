require([
  "esri/map",
  "dojo/domReady!"
], function(Map){
  map = new Map("mapDiv", {
    center: [-56.049, 38.485],
    zoom: 3,
    basemap: "streets"
  });
  function changeBasemap(basemap){
      this.map.setBasemap(basemap)
  }
  window.changeBasemap = changeBasemap;
});