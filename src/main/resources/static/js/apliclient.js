
apiclient=(function(){

	var mockdata=[];

	mockdata["johnconnor"]=	[{author:"johnconnor","points":[{"x":150,"y":120},{"x":215,"y":115}],"name":"house"},
	 {author:"johnconnor","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"}];
	mockdata["maryweyland"]=[{author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
	 {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115},{"x":140,"y":140},{"x":140,"y":140}],"name":"gear2"}];
	mockdata["Saray"] = [
	 {author:"Saray","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"firsblueprint"},
	 {author:"Saray","points":[{"x":70,"y":110},{"x":200,"y":110},{"x":135,"y":170},{"x":70,"y":110},{"x":205,"y":110}],"name":"TriangleZaZ"}]

	return {
		getBlueprintsByAuthor:function(authname,callback){
           const get_request = $.get({
                url: "/blueprints/" + authname,
                contentType: "application/json",
            });
            get_request.then(function (data) {
                callback(data, data);
              }, function (error) {
                alert("The author doesn't exists !")
              }
            );

		},

        getBlueprintsByNameAndAuthor:function(authname,bpname,callback){
            const get_request = $.get({
                url: "/blueprints/"+authname+"/"+bpname,
                contentType: "application/json",
            });
            get_request.then(function(data) {
                if (data===[]){callback(null,null)}
                callback(data,data);
                }, function(error){
                    alert("The author or the blueprint name doesn't exists !");
                }
            );

		},

        // Actualizar un plano (blueprint) con nuevos puntos.
		updateBlueprint:function(points,author,bpname,callback){
		    //Envio de la petición PUT
            const put_request = $.ajax({
                url: "/blueprints/"+author+"/"+bpname+"/"+points,
                type: "PUT",
                data: '{"points":'+JSON.stringify(points)+',"bpname":'+bpname+',"author":'+author+'}',
                contentType: "application/json",
            });callback(null,bpname,bpname)
        },

        //Agregar un nuevo plano, realizando una petición con POST
        addBlueprint:function(points,author,bpname,callback){
            const put_request = $.ajax({
                url: "/blueprints/"+author+"/"+bpname+"/"+points,
                type: "POST", //Creación del recurso
                data: '{"points":'+JSON.stringify(points)+',"bpname":'+bpname+',"author":'+author+'}',
                contentType: "application/json",
            }); callback(null,bpname,bpname);
        },

        //Elimina un plano existente
        deleteBlueprint:function(author,bpname,callback){
           $.ajax({
               url: "/blueprints/"+author+"/"+bpname,
               type: "DELETE", //Petición al servidor
               contentType: "application/json",
           });
        },

        //Buscar planos por autor y nombre
        searchBlueprintsByNameAndAuthor:function(authname,bpname,callback){
            const get_request1 = $.get({
                url: "/blueprints/"+authname+"/", //Obtención de los blueprints del autor
                contentType: "application/json",
            });
            const get_request2 = $.get({
                url: "/blueprints/"+authname+"/"+bpname, //Blueprint específico por nombre
                contentType: "application/json",
            });
            //Resultados(almacenamiento)
            let data1;
            let data2;
            get_request1.then(function(data) {
                console.log(data1);
                data1 = data;
                }, function(error){
                }
            );
            get_request2.then(function(data) {
                console.log(data2);
                data2 = data;
                }, function(error){
                }
            );
        },

	}

})();

