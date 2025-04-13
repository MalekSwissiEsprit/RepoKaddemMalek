package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
//pour pouvoir ordonner le lancement des tests selon
//order travailler
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//une classe de test
@RunWith(SpringRunner.class)
@SpringBootTest
class UniversiteServiceImplTest {
   /* @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private List<Universite> universiteList;
    private Departement departement;

    @BeforeEach
    public void setUp() {
        // Initialisation des objets utilisés pour les tests
        universite = new Universite();
        universite.setIdUniversite(1);
        universite.setNomUniversite("Université Test");

        universiteList = new ArrayList<>();
        universiteList.add(universite);

        Universite universite2 = new Universite();
        universite2.setIdUniversite(2);
        universite2.setNomUniversite("Université 2");
        universiteList.add(universite2);

        departement = new Departement();
        departement.setIdDepartement(1);
        departement.setNomDepartement("Département Test");
    }

    @Test
    public void testRetrieveAllUniversites() {
        // Given
        when(universiteRepository.findAll()).thenReturn(universiteList);

        // When
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Then
        assertEquals(2, result.size());
        assertEquals("Université Test", result.get(0).getNomUniversite());
        assertEquals("Université 2", result.get(1).getNomUniversite());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    public void testAddUniversite() {
        // Given
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // When
        Universite result = universiteService.addUniversite(universite);

        // Then
        assertNotNull(result);
        assertEquals("Université Test", result.getNomUniversite());
        assertEquals(1, result.getIdUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testRetrieveUniversite() {
        // Given
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // When
        Universite result = universiteService.retrieveUniversite(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getIdUniversite());
        assertEquals("Université Test", result.getNomUniversite());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateUniversite() {
        // Given
        Universite updatedUniversite = new Universite();
        updatedUniversite.setIdUniversite(1);
        updatedUniversite.setNomUniversite("Université Modifiée");

        when(universiteRepository.save(any(Universite.class))).thenReturn(updatedUniversite);

        // When
        Universite result = universiteService.updateUniversite(updatedUniversite);

        // Then
        assertNotNull(result);
        assertEquals("Université Modifiée", result.getNomUniversite());
        assertEquals(1, result.getIdUniversite());
        verify(universiteRepository, times(1)).save(updatedUniversite);
    }

    @Test
    public void testDeleteUniversite() {
        // Given
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        doNothing().when(universiteRepository).delete(universite);

        // When
        universiteService.deleteUniversite(1);

        // Then
        verify(universiteRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    public void testAssignUniversiteToDepartement() {
        // Given
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Simuler une liste de départements pour l'université
        List<Departement> departements = new ArrayList<>();
        universite.setDepartements(departements);

        // When
        universiteService.assignUniversiteToDepartement(1, 1);

        // Then
        assertTrue(universite.getDepartements().contains(departement));
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(universite);
    }*/
}