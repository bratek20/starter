rootProject.name = "bratek20-commons"
include("spring:di")
findProject(":spring:di")?.name = "di"
include("spring:web")
findProject(":spring:web")?.name = "web"
