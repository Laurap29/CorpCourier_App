package com.example.corpcourier

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "SolicitudesDB", null, 4) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSolicitudes = """
            CREATE TABLE solicitudes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                telefono TEXT,
                direccion TEXT,
                municipio TEXT,
                descripcion TEXT
            )
        """.trimIndent()

        val createTableUsuarios = """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT UNIQUE,
                password TEXT
            )
        """.trimIndent()

        db.execSQL(createTableSolicitudes)
        db.execSQL(createTableUsuarios)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS solicitudes")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    // --------- MÉTODOS DE USUARIOS ---------

    fun registerUser(usuario: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            // 👇 Guardamos siempre en minúsculas
            put("usuario", usuario.lowercase())
            put("password", password)
        }
        val result = db.insert("usuarios", null, values)
        db.close()

        Log.d("DBHelper", "Usuario registrado-> usuario=${usuario.lowercase()}, resultado=$result")

        return result != -1L
    }

    fun userExists(usuario: String): Boolean {
        val db = readableDatabase
        // 👇 Buscamos siempre en minúsculas
        val cursor = db.rawQuery("SELECT * FROM usuarios WHERE usuario = ?", arrayOf(usuario.lowercase()))
        val exists = cursor.count > 0
        Log.d("DBHelper", "userExists -> usuario=${usuario.lowercase()}, existe=$exists, total=${cursor.count}")
        cursor.close()
        db.close()
        return exists
    }

    fun validateUser(usuario: String, password: String): Boolean {
        val db = readableDatabase
        // 👇 Comparamos usuario en minúsculas, contraseña exacta
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE usuario = ? AND password = ?",
            arrayOf(usuario.lowercase(), password)
        )
        val isValid = cursor.count > 0
        Log.d("DBHelper", "Validando usuario=${usuario.lowercase()}, password=$password, existe=$isValid")
        cursor.close()
        db.close()
        return isValid
    }

    fun getUser(usuario: String, password: String): Usuario? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE usuario = ? AND password = ?",
            arrayOf(usuario.lowercase(), password)
        )

        var user: Usuario? = null
        if (cursor.moveToFirst()) {
            user = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                usuario = cursor.getString(cursor.getColumnIndexOrThrow("usuario")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            )
        }

        Log.d("DBHelper", "getUser -> usuario=${usuario.lowercase()}, encontrado=${user != null}")

        cursor.close()
        db.close()
        return user
    }

    // --------- MÉTODOS DE SOLICITUDES ---------

    fun addSolicitud(solicitud: Solicitud): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", solicitud.nombre)
            put("telefono", solicitud.telefono)
            put("direccion", solicitud.direccion)
            put("municipio", solicitud.municipio)
            put("descripcion", solicitud.descripcion)
        }
        val result = db.insert("solicitudes", null, values)
        db.close()
        Log.d("DBHelper", "addSolicitud -> resultado=$result")
        return result != -1L
    }

    fun getAllSolicitudes(): List<Solicitud> {
        val solicitudes = mutableListOf<Solicitud>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM solicitudes", null)

        if (cursor.moveToFirst()) {
            do {
                val solicitud = Solicitud(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion")),
                    municipio = cursor.getString(cursor.getColumnIndexOrThrow("municipio")),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                )
                solicitudes.add(solicitud)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return solicitudes
    }

    fun deleteSolicitud(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("solicitudes", "id=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun updateSolicitud(solicitud: Solicitud): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", solicitud.nombre)
            put("telefono", solicitud.telefono)
            put("direccion", solicitud.direccion)
            put("municipio", solicitud.municipio)
            put("descripcion", solicitud.descripcion)
        }

        val selection = "id = ?"
        val selectionArgs = arrayOf(solicitud.id.toString())

        val count = db.update(
            "solicitudes",
            values,
            selection,
            selectionArgs
        )
        db.close()
        return count
    }
}
