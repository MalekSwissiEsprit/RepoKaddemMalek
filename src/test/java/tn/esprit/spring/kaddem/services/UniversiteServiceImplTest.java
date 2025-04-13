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
    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private Departement departement;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniv(1);

        universite.setNomUniv("Test University");
        universite.setDepartements(new HashSet<>());

        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Computer Science");
    }

    @Test
    void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> universites = Arrays.asList(universite);
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test University", result.get(0).getNomUniv());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        // Arrange
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Test University", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversite() {
        // Arrange
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite result = universiteService.updateUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Test University", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(1);

        // Assert
        assertNotNull(result);
        assertEquals("Test University", result.getNomUniv());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteUniversite() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        doNothing().when(universiteRepository).delete(any(Universite.class));

        // Act
        universiteService.deleteUniversite(1);

        // Assert
        verify(universiteRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        universiteService.assignUniversiteToDepartement(1, 1);

        // Assert
        assertEquals(1, universite.getDepartements().size());
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        // Arrange
        universite.getDepartements().add(departement);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Act
        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Computer Science", result.iterator().next().getNomDepart());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveDepartementsByUniversite_WhenUniversiteNotFound() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);

        // Assert
        assertNull(result);
        verify(universiteRepository, times(1)).findById(1);
    }
}