package fr.tangv.applimed.database;

import static org.junit.jupiter.api.Assertions.*;

import android.content.Context;

import androidx.room.Room;

import org.junit.jupiter.api.Test;

import fr.tangv.applimed.model.Famille;

class FamilleDAOTest {

    private AMDatabase db;
    private FamilleDAO familleDAO;
    private Famille fam1;
    private Famille fam2;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        this.db = Room.inMemoryDatabaseBuilder(context, AMDatabase.class).build();
        this.familleDAO = db.getFamilleDAO();
        this.fam1 = new Famille("an25", "Anatalgique 25");
        this.fam2 = new Famille("bm98", "Bicorne Medium 98");
    }

    @After
    public void closeDB() {
        this.db.close();
    }

    @Test
    public void insertFamille() {
        this.familleDAO.insertFamille(fam1);
        Famille famGet = this.familleDAO.findFamille(fam1.getCode());
        assertEquals("Test d'insertion d'une famille", fam1, famGet);
        assertTrue(false);
    }

    @Test
    void insertFamilles() {
    }

    @Test
    void deleteFamille() {
    }

    @Test
    void testDeleteFamille() {
    }

    @Test
    void findFamille() {
    }

    @Test
    void findAllFamilles() {
    }

    @Test
    void updateFamille() {
    }

    @Test
    void updateFamilles() {
    }

}