<h1 align="center"><b> PROYETO INTEGRADO DAM</b><img src="https://media.giphy.com/media/hvRJCLFzcasrR4ia7z/giphy.gif" width="35"></h1>
<h1 align="center"><b> INFO PHOTO</b></h1>
<br>
<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif"><br><br>

## <picture><img src = "https://github.com/0xAbdulKhalid/0xAbdulKhalid/raw/main/assets/mdImages/about_me.gif" width = 50px></picture> **INSTALACIÓN PROYECTO**

<picture> <img align="right" src="https://github.com/0xAbdulKhalid/0xAbdulKhalid/raw/main/assets/mdImages/Right_Side.gif" width = 250px></picture>



<br>


## Para montar el proyecto seguiremos los siguientes pasos:

### 1. DESCARGAR EL PROYECTO DE ESTE MISMO GITHUB.
<br>
### 2. IMPORTAR EN ANDROID STUDIO.
<br>
### 3. EJECUTAR EL PROYECTO EN ANDROID STUDIO
<br>


<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif"><br><br>

## <img src="https://media2.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif?cid=ecf05e47a0n3gi1bfqntqmob8g9aid1oyj2wr3ds3mg700bl&rid=giphy.gif" width ="25"><b>  LENGUAJES Y TEGNOLOGIAS USADAS</b>
### TEGNOLOGIAS:
#### ANDROID STUDIO -> PARA DESAROLLAR LA APP EN CODIGO USANDO KOTLIN COMO LENGUAJE PRINCIPAL
#### FIREBASE -> PARA LA AUTENTICACIÓN DE GOOGLE PARA ACCEDER A LA APP Y TAMBIEN EL ALOJAMIENTO DE LAS BASE DE DATOS DE REALTIME Y STORAGE
<br>

### LEGUAJE DE PROGRAMACION:
#### KOTLIN
<br>
<br>
<br>
<br>
### Reglas de Realtime y Storage en firebase

#### En reglas de realtime firebase:
``` json
{
  "rules": {
    ".read": "auth.uid!=null",  
    ".write": "auth.uid!=null",  
  }
 }
```

#### En reglas de firebase storage: 
``` json
rules_version = '2';
  service firebase.storage {
  match /b/{bucket}/o {
  match /{allPaths=**} {
  allow read, write: if
  request.time < timestamp.date(2024, 6, 20);
  }
  }
  }
```
  

<br>
### Proyecto Integrado creado por José Manuel Garcia Travé
