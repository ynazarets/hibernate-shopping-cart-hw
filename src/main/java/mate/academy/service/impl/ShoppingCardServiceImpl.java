package mate.academy.service.impl;

import java.util.Collections;
import mate.academy.dao.ShoppingCardDao;
import mate.academy.dao.TicketDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

@Service
public class ShoppingCardServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCardDao shoppingCardDao;
    @Inject
    private TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        Ticket addedTicket = ticketDao.add(ticket);

        ShoppingCart shoppingCart = shoppingCardDao.getByUser(user).orElseThrow();
        shoppingCart.getTickets().add(addedTicket);
        shoppingCardDao.update(shoppingCart);

    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCardDao.getByUser(user).orElseThrow();
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTickets(Collections.emptyList());
        shoppingCardDao.add(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getTickets().clear();
        shoppingCardDao.update(shoppingCart);
    }
}
