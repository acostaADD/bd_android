package com.example.myapplication.baseDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos

        // Nombre de la tabla y columnas
        private const val TABLE_CONTACTS = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
    }

    // Crear la tabla cuando se crea la base de datos
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = """
            CREATE TABLE $TABLE_CONTACTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT
            )
        """
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    // Actualizar la base de datos cuando la versión cambia
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    // Función para agregar un nuevo contacto
    fun addContact(name: String, phone: String) {
        val db = writableDatabase // Usamos la base de datos en modo escritura
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
        }
        db.insert(TABLE_CONTACTS, null, values)
        db.close() // Cerrar la base de datos después de la inserción
    }

    // Función para obtener todos los contactos
    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = readableDatabase // Usamos la base de datos en modo lectura
        val cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null)

        // Iteramos sobre los resultados del cursor
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            contacts.add(Contact(id, name, phone))
        }

        cursor.close() // Cerrar el cursor
        db.close() // Cerrar la base de datos
        return contacts
    }

    // Función para eliminar un contacto por ID
    fun deleteContact(contactId: Int) {
        val db = writableDatabase // Usamos la base de datos en modo escritura
        db.delete(TABLE_CONTACTS, "$COLUMN_ID = ?", arrayOf(contactId.toString()))
        db.close() // Cerrar la base de datos
    }
}
