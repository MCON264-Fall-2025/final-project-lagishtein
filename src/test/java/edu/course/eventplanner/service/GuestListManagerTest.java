package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestListManagerTest {

    private GuestListManager guestListManager;
    private Guest guest1;
    private Guest guest2;
    private Guest guest3;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
        guest1 = new Guest("John Doe", "family");
        guest2 = new Guest("Jane Smith", "friends");
        guest3 = new Guest("Bob Johnson", "coworkers");
    }

    @Nested
    @DisplayName("AddGuest Tests")
    class AddGuestTests {

        @Test
        @DisplayName("Should add a valid guest successfully")
        void testAddValidGuest() {
            guestListManager.addGuest(guest1);

            assertEquals(1, guestListManager.getGuestCount());
            assertEquals(guest1, guestListManager.findGuest("John Doe"));
        }

        @Test
        @DisplayName("Should add multiple guests successfully")
        void testAddMultipleGuests() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            assertEquals(3, guestListManager.getGuestCount());
            assertNotNull(guestListManager.findGuest("John Doe"));
            assertNotNull(guestListManager.findGuest("Jane Smith"));
            assertNotNull(guestListManager.findGuest("Bob Johnson"));
        }

        @Test
        @DisplayName("Should handle null guest gracefully")
        void testAddNullGuest() {
            guestListManager.addGuest(null);

            assertEquals(0, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should handle guest with null name gracefully")
        void testAddGuestWithNullName() {
            Guest guestWithNullName = new Guest(null, "family");
            guestListManager.addGuest(guestWithNullName);

            assertEquals(0, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should allow duplicate names (last one overwrites in map)")
        void testAddDuplicateNames() {
            Guest duplicate = new Guest("John Doe", "friends");

            guestListManager.addGuest(guest1);
            guestListManager.addGuest(duplicate);

            assertEquals(2, guestListManager.getGuestCount());
            // The map will have the last added guest with the same name
            Guest found = guestListManager.findGuest("John Doe");
            assertEquals("friends", found.getGroupTag());
        }
    }

    @Nested
    @DisplayName("RemoveGuest Tests")
    class RemoveGuestTests {

        @Test
        @DisplayName("Should remove an existing guest successfully")
        void testRemoveExistingGuest() {
            guestListManager.addGuest(guest1);

            assertTrue(guestListManager.removeGuest("John Doe"));
            assertEquals(0, guestListManager.getGuestCount());
            assertNull(guestListManager.findGuest("John Doe"));
        }

        @Test
        @DisplayName("Should return false when removing non-existent guest")
        void testRemoveNonExistentGuest() {
            guestListManager.addGuest(guest1);

            assertFalse(guestListManager.removeGuest("Non Existent"));
            assertEquals(1, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should handle null name gracefully")
        void testRemoveNullName() {
            guestListManager.addGuest(guest1);

            assertFalse(guestListManager.removeGuest(null));
            assertEquals(1, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should remove correct guest from multiple guests")
        void testRemoveFromMultipleGuests() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            assertTrue(guestListManager.removeGuest("Jane Smith"));
            assertEquals(2, guestListManager.getGuestCount());
            assertNull(guestListManager.findGuest("Jane Smith"));
            assertNotNull(guestListManager.findGuest("John Doe"));
            assertNotNull(guestListManager.findGuest("Bob Johnson"));
        }

        @Test
        @DisplayName("Should handle removing from empty list")
        void testRemoveFromEmptyList() {
            assertFalse(guestListManager.removeGuest("John Doe"));
            assertEquals(0, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should remove all guests when called multiple times")
        void testRemoveAllGuests() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            assertTrue(guestListManager.removeGuest("John Doe"));
            assertTrue(guestListManager.removeGuest("Jane Smith"));
            assertTrue(guestListManager.removeGuest("Bob Johnson"));

            assertEquals(0, guestListManager.getGuestCount());
        }
    }

    @Nested
    @DisplayName("FindGuest Tests")
    class FindGuestTests {

        @Test
        @DisplayName("Should find an existing guest")
        void testFindExistingGuest() {
            guestListManager.addGuest(guest1);

            Guest found = guestListManager.findGuest("John Doe");

            assertNotNull(found);
            assertEquals("John Doe", found.getName());
            assertEquals("family", found.getGroupTag());
        }

        @Test
        @DisplayName("Should return null for non-existent guest")
        void testFindNonExistentGuest() {
            guestListManager.addGuest(guest1);

            assertNull(guestListManager.findGuest("Non Existent"));
        }

        @Test
        @DisplayName("Should return null for null name")
        void testFindNullName() {
            guestListManager.addGuest(guest1);

            assertNull(guestListManager.findGuest(null));
        }

        @Test
        @DisplayName("Should find correct guest from multiple guests")
        void testFindFromMultipleGuests() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            Guest found = guestListManager.findGuest("Jane Smith");

            assertNotNull(found);
            assertEquals("Jane Smith", found.getName());
            assertEquals("friends", found.getGroupTag());
        }

        @Test
        @DisplayName("Should return null when searching empty list")
        void testFindInEmptyList() {
            assertNull(guestListManager.findGuest("John Doe"));
        }

        @Test
        @DisplayName("Should be case-sensitive")
        void testFindCaseSensitive() {
            guestListManager.addGuest(guest1);

            assertNull(guestListManager.findGuest("john doe"));
            assertNull(guestListManager.findGuest("JOHN DOE"));
            assertNotNull(guestListManager.findGuest("John Doe"));
        }
    }

    @Nested
    @DisplayName("GetGuestCount Tests")
    class GetGuestCountTests {

        @Test
        @DisplayName("Should return 0 for empty list")
        void testGetCountEmpty() {
            assertEquals(0, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should return correct count after adding guests")
        void testGetCountAfterAdding() {
            guestListManager.addGuest(guest1);
            assertEquals(1, guestListManager.getGuestCount());

            guestListManager.addGuest(guest2);
            assertEquals(2, guestListManager.getGuestCount());

            guestListManager.addGuest(guest3);
            assertEquals(3, guestListManager.getGuestCount());
        }

        @Test
        @DisplayName("Should return correct count after removing guests")
        void testGetCountAfterRemoving() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            guestListManager.removeGuest("Jane Smith");
            assertEquals(2, guestListManager.getGuestCount());

            guestListManager.removeGuest("John Doe");
            assertEquals(1, guestListManager.getGuestCount());
        }
    }

    @Nested
    @DisplayName("GetAllGuests Tests")
    class GetAllGuestsTests {

        @Test
        @DisplayName("Should return empty list initially")
        void testGetAllGuestsEmpty() {
            List<Guest> guests = guestListManager.getAllGuests();

            assertNotNull(guests);
            assertEquals(0, guests.size());
        }

        @Test
        @DisplayName("Should return all added guests")
        void testGetAllGuests() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            List<Guest> guests = guestListManager.getAllGuests();

            assertEquals(3, guests.size());
            assertTrue(guests.contains(guest1));
            assertTrue(guests.contains(guest2));
            assertTrue(guests.contains(guest3));
        }

        @Test
        @DisplayName("Should maintain insertion order")
        void testGetAllGuestsMaintainsOrder() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            List<Guest> guests = guestListManager.getAllGuests();

            assertEquals(guest1, guests.get(0));
            assertEquals(guest2, guests.get(1));
            assertEquals(guest3, guests.get(2));
        }

        @Test
        @DisplayName("Should reflect changes after removal")
        void testGetAllGuestsAfterRemoval() {
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            guestListManager.addGuest(guest3);

            guestListManager.removeGuest("Jane Smith");

            List<Guest> guests = guestListManager.getAllGuests();

            assertEquals(2, guests.size());
            assertFalse(guests.contains(guest2));
            assertTrue(guests.contains(guest1));
            assertTrue(guests.contains(guest3));
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should handle complex add/remove/find operations")
        void testComplexOperations() {
            // Add guests
            guestListManager.addGuest(guest1);
            guestListManager.addGuest(guest2);
            assertEquals(2, guestListManager.getGuestCount());

            // Find a guest
            assertNotNull(guestListManager.findGuest("John Doe"));

            // Remove a guest
            assertTrue(guestListManager.removeGuest("John Doe"));
            assertEquals(1, guestListManager.getGuestCount());

            // Add another guest
            guestListManager.addGuest(guest3);
            assertEquals(2, guestListManager.getGuestCount());

            // Verify the correct guests remain
            assertNull(guestListManager.findGuest("John Doe"));
            assertNotNull(guestListManager.findGuest("Jane Smith"));
            assertNotNull(guestListManager.findGuest("Bob Johnson"));
        }

        @Test
        @DisplayName("Should handle re-adding removed guest")
        void testReAddRemovedGuest() {
            guestListManager.addGuest(guest1);
            guestListManager.removeGuest("John Doe");
            guestListManager.addGuest(guest1);

            assertEquals(1, guestListManager.getGuestCount());
            assertNotNull(guestListManager.findGuest("John Doe"));
        }

        @Test
        @DisplayName("Should handle edge case with empty strings")
        void testEmptyStringName() {
            Guest emptyNameGuest = new Guest("", "family");
            guestListManager.addGuest(emptyNameGuest);

            assertEquals(1, guestListManager.getGuestCount());
            assertNotNull(guestListManager.findGuest(""));
        }

        @Test
        @DisplayName("Should handle guests with special characters in names")
        void testSpecialCharactersInName() {
            Guest specialGuest = new Guest("O'Brien-Smith", "family");
            guestListManager.addGuest(specialGuest);

            assertEquals(1, guestListManager.getGuestCount());
            assertNotNull(guestListManager.findGuest("O'Brien-Smith"));
            assertTrue(guestListManager.removeGuest("O'Brien-Smith"));
        }
    }
}

