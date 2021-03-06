String.prototype.trim = function() {
	return (this.replace(/^[\s\u3000]+|[\s\u3000]+$/g, ''))}
	//return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""))}

String.prototype.startsWith = function(str)	{
	return (this.match("^"+str)==str)}

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
	}

String.prototype.replaceAll = function(stringToFind,stringToReplace){
    var temp = this;
    var index = temp.indexOf(stringToFind);
        while(index != -1){
            temp = temp.replace(stringToFind,stringToReplace);
            index = temp.indexOf(stringToFind);
        }
        return temp;
    }

String.prototype.equalsIgnoreCase = function (str) {
  return this.toLowerCase() === str.toLowerCase();
}
