//

apimock=(function(){

	var mockdata = [];

        mockdata["johnconnor"] = [
                {author: "johnconnor", "points": [{"x": 150, "y": 120}, {"x": 215, "y": 115}], "name": "house"},
                {author: "johnconnor", "points": [{"x": 340, "y": 240}, {"x": 15, "y": 215}], "name": "star"},
                {author: "johnconnor", "points": [{"x": 170, "y": 230}, {"x": 415, "y": 205}], "name": "diamond"},
                {author: "johnconnor", "points": [{"x": 505, "y": 450}, {"x": 305, "y": 31}], "name": "arena"}];

            mockdata["maryweyland"] = [
                {author: "maryweyland", "points": [{"x": 140, "y": 140}, {"x": 115, "y": 115}], "name": "house2"},
                {author: "maryweyland", "points": [{"x": 160, "y": 230}, {"x": 189, "y":925}], "name": "star2"},
                {author: "maryweyland", "points": [{"x": 40, "y": 10}, {"x": 15, "y": 105}], "name": "diamond2"},
                {author: "maryweyland", "points": [{"x": 100, "y": 40}, {"x": 5, "y": 125}], "name": "arena2"}];

            mockdata["Saray"] = [
            	 {author:"Saray","points":[{"x":134,"y":234},{"x":125,"y":235}],"name":"firsblueprint"},
            	 {author:"Saray","points":[{"x":37,"y":110},{"x":250,"y":192},{"x":135,"y":170},{"x":70,"y":110},{"x":205,"y":110}],"name":"TriangleZaZ"}]

                 var plano2 = {"author":"zoe","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"secondBlueprint"};
                 var plano3 = {"author":"drew","points":[{"x":30,"y":10},{"x":250,"y":55}],"name":"thirdBlueprint"}



	return {
		getBlueprintsByAuthor:function(authname,callback){
			callback(mockdata[authname.toLowerCase()],mockdata[authname.toLowerCase()]);
		},
        getBlueprintsByNameAndAuthor:function(authname,bpname,callback){
			let blueprint = mockdata[authname].find(function (blueprint) {
                            return blueprint.name === bpname;
                        });
                        callback(null, blueprint);

		}
	}

})();