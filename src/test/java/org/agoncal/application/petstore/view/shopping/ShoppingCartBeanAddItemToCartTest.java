
package org.agoncal.application.petstore.view.shopping;

import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.model.ShoppingCartItem;
import org.agoncal.application.petstore.service.CatalogService;
import org.agoncal.application.petstore.util.Loggable;
import org.agoncal.application.petstore.view.AbstractBean;
import org.agoncal.application.petstore.view.CatchException;
import org.agoncal.application.petstore.view.LoggedIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.model.*;
import org.agoncal.application.petstore.service.PurchaseOrderService;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

class ShoppingCartBeanAddItemToCartTest {

	private ShoppingCartBean shoppingCartBean;

	@Mock
	private CatalogService catalogBean;

	@Mock
	private Conversation conversation;

	@Mock
	private FacesContext facesContext;

	private List<ShoppingCartItem> cartItems;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		shoppingCartBean = new ShoppingCartBean();
		shoppingCartBean.catalogBean = catalogBean;
		shoppingCartBean.conversation = conversation;
		cartItems = new ArrayList<>();
		shoppingCartBean.cartItems = cartItems;
	}

	@Test
	@Tag("valid")
	void addNewItemToEmptyCart() {
		Item item = new Item();
		when(catalogBean.findItem(anyString())).thenReturn(item);
		when(conversation.isTransient()).thenReturn(true);
		String result = shoppingCartBean.addItemToCart();
		verify(conversation).begin();
		assertTrue(shoppingCartBean.cartItems instanceof ArrayList);
		assertEquals(1, shoppingCartBean.cartItems.size());
		assertEquals(item, shoppingCartBean.cartItems.get(0).getItem());
		assertEquals(1, shoppingCartBean.cartItems.get(0).getQuantity());
		assertEquals("showcart.faces", result);
	}

	@Test
	@Tag("valid")
	void addExistingItemToCart() {
		Item item = new Item();
		when(catalogBean.findItem(anyString())).thenReturn(item);
		when(conversation.isTransient()).thenReturn(false);
		ShoppingCartItem existingCartItem = new ShoppingCartItem(item, 1);
		cartItems.add(existingCartItem);
		String result = shoppingCartBean.addItemToCart();
		verify(conversation, never()).begin();
		assertEquals(1, shoppingCartBean.cartItems.size());
		assertEquals(2, shoppingCartBean.cartItems.get(0).getQuantity());
		assertEquals("showcart.faces", result);
	}

	@Test
	@Tag("valid")
	void addItemWhenConversationStarted() {
		Item item = new Item();
		when(catalogBean.findItem(anyString())).thenReturn(item);
		when(conversation.isTransient()).thenReturn(false);
		cartItems.add(new ShoppingCartItem(new Item(), 1));
		String result = shoppingCartBean.addItemToCart();
		verify(conversation, never()).begin();
		assertEquals(2, shoppingCartBean.cartItems.size());
		assertEquals(item, shoppingCartBean.cartItems.get(1).getItem());
		assertEquals(1, shoppingCartBean.cartItems.get(1).getQuantity());
		assertEquals("showcart.faces", result);
	}

	@Test
    @Tag("invalid")
    void handleInvalidItemId() {
        when(catalogBean.findItem(anyString())).thenReturn(null);
        String result = shoppingCartBean.addItemToCart();
        assertEquals(0, shoppingCartBean.cartItems.size());
        assertNull(result);
    }

	@Test
	@Tag("boundary")
	void testConversationManagement() {
		Item item = new Item();
		when(catalogBean.findItem(anyString())).thenReturn(item);
		// Test case 1: Conversation is transient (new conversation)
		when(conversation.isTransient()).thenReturn(true);
		String result1 = shoppingCartBean.addItemToCart();
		verify(conversation).begin();
		assertTrue(shoppingCartBean.cartItems instanceof ArrayList);
		// Test case 2: Conversation is already active (ongoing conversation)
		when(conversation.isTransient()).thenReturn(false);
		String result2 = shoppingCartBean.addItemToCart();
		verify(conversation, never()).begin(); // Conversation should not be restarted
		assertSame(shoppingCartBean.cartItems, cartItems); // Existing cartItems list
															// should be used
	}

}