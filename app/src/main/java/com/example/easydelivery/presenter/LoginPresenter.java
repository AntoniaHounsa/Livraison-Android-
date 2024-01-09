package com.example.easydelivery.presenter;
import com.example.easydelivery.repository.UserRepository;
import com.example.easydelivery.uiContract.ILoginView;

public class LoginPresenter implements ILoginPresenter{
    private ILoginView view;
    private UserRepository userRepository;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.userRepository = new UserRepository();
    }

    @Override
    public void login(String email, String password) {
        userRepository.login(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie, j'essaie de faire l'obtention l'UID pour chercher le role
                        String userId = task.getResult().getUser().getUid();
                        userRepository.getUserRole(userId)
                                .addOnCompleteListener(roleTask -> {
                                    if (roleTask.isSuccessful()) {
                                        String role = roleTask.getResult().getString("role");
                                        view.navigateToLogin(role);
                                    } else {
                                        view.showErrorMessage("Impossible de récupérer le rôle de l'utilisateur");
                                    }
                                });
                    } else {
                        view.showErrorMessage("Connexion échouée");
                    }
                });
    }

}
