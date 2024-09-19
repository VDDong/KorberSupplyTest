Hola Cecilia,

Te adjunto la tarea completada. Queria comentar dos cosas:

1. Como tendras oportunidad de comprobar ArticleManager no tiene mucha logica de negocio porque es un simple CRUD, en una aplicación normal la logica de una base de datos se encargaria de casi todo en el CRUD. Para compensar puse la logica que seguiria la base de datos (+ la logica de un mapper) en un mockDao para mantenerlo lo más fiel posible a una aplicación normal
2. Hice 2 tests unitarios pero hice 11 de integración para el caso "correcto" y un caso basico "erroneo" que cubren 95% del sistema, espero que eso tambien te sirva, queria hacer los dos para mostrarte que puedo (tambien me deje llevar un poco).

Tambien he dejado unos comentarios para decisiones de diseño que a lo mejor no quedaron muy claras, he tenido oportunidad de utilizar herencia y otras estrategias de programación, añadi unas clases para error handling para que el postman tenga más información a la hora de devolver un error, pruebalas 😁. Aprendi mucho y me diverti bastante, o sea que muchas gracias.

P.S. Tuve que quitar los ejecutables de maven del projecto para que me dejase adjuntarlo, supongo que no sera un problema ya que seguramente lo tengas, pero por si a caso

Amablemente,

Vicente Duarte Dong
