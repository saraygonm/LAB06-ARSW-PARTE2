var app = (function () {

  let authorName = "";
  let author;
  let points = [];


    //añadir nuevo punto al array de tuplas
    function setPoints(tuple){
        points.push(tuple); //push: inserción al final del array
    }

    //Obtención de todos los puntos almacenadios
    function getPoints(){
        return points;
    }

    //Nombre del autor y Validación de existencia
    function getNameAuthorBlueprints() {
       author = $("#author").val();
       if (author === "") {
           alert("Incorrect name !");
       } else {
           apiclient.getBlueprintsByAuthor(author, (req, resp) => {
               createTableData(resp);
           });
       }
    }

    //Digujo de lineas de acuerdo a los puntos
    function getBlueprintsByNameAndAuthor(author,name) {
       if (author === "") {
           alert("Incorrect name !");
       } else {
        apiclient.getBlueprintsByNameAndAuthor(author,name, (req, resp) => {
                draw(resp);
         });
       }
    }

    //Lienzo canvas en donde se visualizará el plano
    function draw(data){
        const points = data.points;
        var c = document.getElementById("myCanvas");
        var ctx = c.getContext("2d");
        ctx.clearRect(0, 0, c.width, c.height);
        ctx.restore();
        ctx.beginPath();
        for (let i = 0; i<points.length-1 ; i++){
            ctx.moveTo(points[i].x, points[i].y);
            ctx.lineTo(points[i+1].x, points[i+1].y);
            ctx.stroke();
        }
    }

    // Tabla con puntajes
    function createTableData(data) {
        let table = $("#fl-table tbody");
        table.empty();
        if (data !== undefined) {
          $("#author-name").text(author + "'s " + "blueprints:");
          const datanew = data.map((blueprint) => {
              return {
                  name: blueprint.name,
                  puntos: blueprint.points.length
              }
          });
          datanew.forEach(({name, puntos}) => {
          table.append(
                          `<tr>
                            <td>${name}</td>
                            <td>${puntos}</td>
                          `
                          +"<td> <button onclick= app.getBlueprintsByNameAndAuthor('"+$("#author").val()+"','"+name+"')>Open</button></td></tr>"
                      );
                  })
          const totalPuntos = datanew.reduce((suma, {puntos}) => suma + puntos, 0);
          $("#totalPoints").text(totalPuntos);
        } else {
            alert("Author dont found!");
            $("#author-name").empty();
            $("#totalPoints").empty();
        }
    }

    // Agregar un nuevo blueprint (plano) para un autor.
    function addBlueprint(){
        author = $("#author").val(); //obtención autor
        let bpname = prompt('Insert the name of the new bpname');
        let data = "";
        apiclient.getBlueprintsByNameAndAuthor(author,bpname, (req, resp) => {
            data = resp;
            console.log(typeof resp);
        });
                apiclient.addBlueprint(parseString(points),author,bpname, (req, resp) => {
                    window.setTimeout(function(){
                        getBlueprintsByNameAndAuthor(author,resp);
                    }, 600);
                });
        points=[];
        getNameAuthorBlueprints();
    }

    // Convertir un array de puntos en una cadena de texto (string) con el formato adecuado.
    //"{'x':140,'y':140},{'x':115,'y':115}"
    function parseString(array){
        let string = "";
        for (let i = 0; i<array.length; i++){
            string += "{'x':"+array[i][0] +",'y':"+array[i][1]+"}" ;
            if (i!=array.length-1) string+=",";
        }
        return string;
    }

    //Actualizar un blueprint existente con nuevos puntos.
    function updateBlueprint() {
        let bp = [{"x":140,"y":140},{"x":115,"y":115}];
        author = $("#author").val();
        let bpname = "firstBlueprint";
        apiclient.updateBlueprint(author,bpname, (req, resp) => {
           getBlueprintsByNameAndAuthor(author,resp);
        });
    }

    //Eliminar
    function deleteBlueprint() {
        author = $("#author").val();
        let bpname = prompt('Insert the name of the blueprint you want to delete');
        const canvas = document.getElementById('myCanvas');
        const context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);
        apiclient.deleteBlueprint(author,bpname, (req, resp) => {
        });
        getNameAuthorBlueprints();
    }

  return {
    getNameAuthorBlueprints: getNameAuthorBlueprints,
    getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor,
    updateBlueprint: updateBlueprint,
    deleteBlueprint: deleteBlueprint,
    setPoints : setPoints,
    getPoints : getPoints,
    addBlueprint : addBlueprint,
    deleteBlueprint : deleteBlueprint
  };

})();