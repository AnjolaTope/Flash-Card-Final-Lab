package com.example.flashcard;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.flashcard.Flashcard.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashcardDao flashcardDao();
}