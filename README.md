Feladat
=======
Valósítsd meg a webböngészőn keresztül feltöltött képek tárolását és digitális aláírását. A rendszer JavaScript frontend-del és Java backend-del rendelkezik, melyek közül a fájl feltöltés kerete már implementálásra került. A tárolás megoldása és a kiszolgálás befejezése már a Te feladatod.
A feltöltött képek maximális mérete 2MB lehet, típusára vonatkozólag nincs megkötés. A digitális aláírás bónusz feladat, a képek tárolása és kiszolgálása a fő feladat.
Fontos, hogy a későbbiekben legyen lehetőség az alkalmazás továbbfejlesztésére, valamint a kód legyen érthető más fejlesztők számára is.

Extra információk
-----------------
Az aláíráshoz használandó kulcsot a projekt könyvtárában, PKCS8 formátumban találod. Az aláírás SHA256withRSA algoritmussal legyen megvalósítva, majd ennek eredménye BASE64 algoritmus használatával legyen kódolva. Az így kapott eredmény legyen megjelenítve a frontend-en is. 

Fejlesztési tudnivalók
======================
Az alkalmazás Spring Boot keretrendszeren alapszik és `mvn spring-boot:run` paranccsal vagy a Main fájl futtatásával lehet indítani. Sikeres build-et követően a http://localhost:8080/ui url-en érhető el a fájlfeltöltő komponens és a listázó, mely kezdeti állapotban üres. Fájl felöltés drag&drop használatával vagy kattintással és fájl kiválasztással lehetséges.
Új fájl feltöltése a "Drop files here to upload" gomb megnyomása után lehetséges.

A feladat elvégezéshez biztosított fájlok melyek a projektben megtalálhatók:
    * PKCS8 aláíró kulcs
    * Néhány teszt fájl és a hozzájuk tartozó BASE64 kódolt aláírás
	
Kiegészítések
=============
 - A fájlfeltöltő szerver oldali osztályának a váza UploadController.java fájlban található. 
 - A képek listázásához, szükséges implementálni a klienst kiszolgáló végpontokat. 
 
Az alábbi képen egy példát kívánunk mutatni a helyes megoldásra vonatkozólag:
 ![Solution](image/example.png)

