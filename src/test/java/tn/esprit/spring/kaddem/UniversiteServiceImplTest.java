package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Use MockitoExtension instead of SpringExtension
@ExtendWith(SpringExtension.class)
//pour pouvoir ordonner le lancement des tests selon
//order travailler
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//une classe de test
@SpringBootTest
public class UniversiteServiceImplTest {
    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    private Universite universite;
    private Departement departement;

    @Captor
    private ArgumentCaptor<Universite> universiteCaptor;
    // Test avancé : Récupérer toutes les universités avec une liste vide
    @BeforeEach
    public void setUp() {
        universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("Université Test");

        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Département Test");

        // Initialize the departements list to avoid NullPointerException
       /* universite.setDepartements(new java.util.ArrayList<>());*/
    }

    @Test
    @Order(1)
    public void testRetrieveAllUniversites_EmptyList() {
        when(universiteRepository.findAll()).thenReturn(Collections.emptyList());
        List<Universite> result = universiteService.retrieveAllUniversites();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testAjouterUniversite() {
        Universite universiteToAdd = new Universite();
        universiteToAdd.setNomUniv("Université Nouvelle");
        when(universiteRepository.save(any(Universite.class))).thenAnswer(invocation -> {
            Universite savedUniversite = invocation.getArgument(0);
            savedUniversite.setIdUniv(100);
            return savedUniversite;
        });
        Universite savedUniversite = universiteService.addUniversite(universiteToAdd);
        assertNotNull(savedUniversite.getIdUniv());
        assertEquals("Université Nouvelle", savedUniversite.getNomUniv());
        assertTrue(savedUniversite.getNomUniv().length() < 50);
        doNothing().when(universiteRepository).deleteById(savedUniversite.getIdUniv());
        universiteService.deleteUniversite(savedUniversite.getIdUniv());
        verify(universiteRepository, times(1)).deleteById(savedUniversite.getIdUniv());
    }

    @Test
    @Order(3)
    public void testAddUniversite_ThrowsExceptionOnNull() {
        Universite universiteNull = null;
        assertThrows(IllegalArgumentException.class, () -> universiteService.addUniversite(universiteNull));
        verify(universiteRepository, never()).save(any(Universite.class));
    }

    @Test
    @Order(4)
    public void testUpdateUniversite_UsingArgumentCaptor() {
        universite.setNomUniv("Université Modifiée");
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);
        Universite result = universiteService.updateUniversite(universite);
        assertEquals("Université Modifiée", result.getNomUniv());
        verify(universiteRepository).save(universiteCaptor.capture());
        Universite capturedUniversite = universiteCaptor.getValue();
        assertEquals("Université Modifiée", capturedUniversite.getNomUniv());
    }

    @Test
    @Order(5)
    public void testRetrieveUniversite_NotFound() {
        when(universiteRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> universiteService.retrieveUniversite(999));
        verify(universiteRepository, times(1)).findById(999);
    }

    @Test
    @Order(6)
    public void testDeleteUniversite_NotFound() {
        doThrow(new IllegalArgumentException("Université non trouvée")).when(universiteRepository).deleteById(999);
        assertThrows(IllegalArgumentException.class, () -> universiteService.deleteUniversite(999));
        verify(universiteRepository, times(1)).deleteById(999);
    }

    @Test
    @Order(7)
    public void testAssignUniversiteToDepartement_ComplexScenario() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(any(Universite.class))).thenAnswer(invocation -> invocation.getArgument(0));
        universiteService.assignUniversiteToDepartement(1, 1);
        verify(universiteRepository).save(universiteCaptor.capture());
        Universite capturedUniversite = universiteCaptor.getValue();
        assertTrue(capturedUniversite.getDepartements().contains(departement));
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    @Order(8)
    public void testRetrieveDepartementsByUniversite_UniversiteNotFound() {
        when(universiteRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> universiteService.retrieveDepartementsByUniversite(999));
        verify(universiteRepository, times(1)).findById(999);
    }

    @Test
    @Order(9)
    public void testAssignUniversiteToDepartement_VerifyOrder() {
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);
        universiteService.assignUniversiteToDepartement(1, 1);
        InOrder inOrder = inOrder(universiteRepository, departementRepository);
        inOrder.verify(universiteRepository).findById(1);
        inOrder.verify(departementRepository).findById(1);
        inOrder.verify(universiteRepository).save(any(Universite.class));
    }

    @Test
    @Order(10)
    public void testRetrieveAllUniversites_RepositoryThrowsException() {
        when(universiteRepository.findAll()).thenThrow(new RuntimeException("Erreur base de données"));
        assertThrows(RuntimeException.class, () -> universiteService.retrieveAllUniversites());
        verify(universiteRepository, times(1)).findAll();
    }
}