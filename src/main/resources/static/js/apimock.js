//

apimock=(function(){

	var mockdata = [];

        mockdata["johnconnor"]=	[{author:"johnconnor","points":[{"x":150,"y":120},{"x":215,"y":115}],"name":"house"},
        {author:"johnconnor","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"}
        ];
        mockdata["maryweyland"]=[{author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
        {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115},{"x":140,"y":140},{"x":140,"y":140}],"name":"gear2"}
        ];
        mockdata["Saray"] = [
        {author:"Saray","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"firsblueprint"},
        {author:"Saray","points":[{"x":70,"y":110},{"x":200,"y":110},{"x":135,"y":170},{"x":70,"y":110},{"x":205,"y":110}],"name":"TriangleZaZ"}]
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