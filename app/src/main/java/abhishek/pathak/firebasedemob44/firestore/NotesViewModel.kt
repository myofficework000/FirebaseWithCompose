package abhishek.pathak.firebasedemob44.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class NotesViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes
    private val firebaseFireStore = FirebaseFirestore.getInstance()
    private val notesCollection = firebaseFireStore.collection("notes")

    fun fetchNotes() {
        notesCollection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val notesList = ArrayList<Note>()
                for (item in task.result) {
                    val note = item.toObject(Note::class.java)
                    notesList.add(note)
                }

                if (notesList.isNotEmpty()) {
                    _notes.value = notesList
                }
            }
        }
    }

    fun addNotes(note: Note) {
        notesCollection.add(note).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fetchNotes()
            }
        }
    }
}