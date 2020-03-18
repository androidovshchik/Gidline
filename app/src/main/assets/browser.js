function getWidth() {
  return Math.max(
    document.body.scrollWidth,
    document.documentElement.scrollWidth,
    document.body.offsetWidth,
    document.documentElement.offsetWidth,
    document.documentElement.clientWidth
  );
}
var width = "device-width";
var viewport = document.querySelector("meta[name=viewport]");
if (viewport) {
	viewport.setAttribute('content', 'width=' + width + ', initial-scale=1.0');
} else {
	viewport = document.createElement('meta');
	viewport.name = "viewport";
	viewport.content = "width=" + width + ", initial-scale=1.0";
	document.getElementsByTagName('head')[0].appendChild(viewport);
}
var scale = 'scale(' + (width / getWidth()) + ')';
console.log(scale);
console.log(width);
console.log(getWidth());
if (document.body.style.transform) {
    document.body.style.transform = scale;
} else if (document.body.style.webkitTransform) {
    document.body.style.webkitTransform = scale;
}