package edu.school21.numbers;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password)
            throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = usersRepository.findByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        if (user.isAuthStatus()) {
            throw new AlreadyAuthenticatedException();
        }
        if (user.getPassword().equals(password)) {
            user.setAuthStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
