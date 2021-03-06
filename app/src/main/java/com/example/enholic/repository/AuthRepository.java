package com.example.enholic.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.enholic.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthRepository {
    //User authentication
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth firebaseAuth;

    //User profile
    private FirebaseFirestore firebaseFirestore;
    private AuthRepository.OnUserLoad onUserLoad;
    private UserModel userModel;

    public AuthRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();

        //User profile
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public AuthRepository(Application application, AuthRepository.OnUserLoad onUserLoad) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();

        //User profile
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onUserLoad = onUserLoad;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signUp(String email, String pass, String name){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    // Sign in is successful
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                    createUserProfile(user.getUid());
                    createUserWord(user.getUid());
                    createUserReward(user.getUid());
                }
                else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signIn(String email, String pass){
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    loadUserProfile(firebaseAuth.getCurrentUser().getUid());
                }
                else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signOut(){
        firebaseAuth.signOut();
    }

    //User profile
    public UserModel getUserModel() {
        return userModel;
    }

    private void createUserProfile(String userID) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("level", "beginner");
        newUser.put("currentEx", 0);
        newUser.put("bGb", 0);
        newUser.put("sGb", 0);
        newUser.put("enPoint", 0);
        firebaseFirestore.collection("User").document(userID)
                .set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AuthRepository", "New User successfully written!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AuthRepository", "Error create user!", e);
                    }
                });
    }

    private void createUserWord(String userID) {
        List<String> word = new ArrayList<>();
        Map<String, Object> newUserWord = new HashMap<>();
        newUserWord.put("word", word);
        firebaseFirestore.collection("User_Word").document(userID)
                .set(newUserWord).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AuthRepository", "New User Word successfully written!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AuthRepository", "Error create user word!", e);
                    }
                });
    }

    private void createUserReward(String userID) {
        List<String> reward = new ArrayList<>();
        Map<String, Object> newUserReward = new HashMap<>();
        newUserReward.put("reward", reward);
        firebaseFirestore.collection("User_Reward").document(userID)
                .set(newUserReward).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AuthRepository", "New User Reward successfully written!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AuthRepository", "Error create user reward!", e);
                    }
                });
    }

    public void loadUserProfile(String userID) {
        firebaseFirestore.collection("User").document(userID)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.d("AuthRepository", documentSnapshot.toString());

                    UserModel userModel_temp = new UserModel();
                    userModel_temp.setLevel((String) documentSnapshot.get("level"));
                    userModel_temp.setEnPoint((Long) documentSnapshot.get("enPoint"));
                    userModel_temp.setCurrentEx((Long) documentSnapshot.get("currentEx"));
                    userModel_temp.setbGb((Long) documentSnapshot.get("bGb"));
                    userModel_temp.setsGb((Long) documentSnapshot.get("sGb"));
                    onUserLoad.onLoad(userModel_temp);
                }
                else {
                    onUserLoad.onError(new Exception("No data"));
                }

            }
        });
    }

    public void updateUserProfileEnPoint(String userID, Long newEnPoint) {
        firebaseFirestore.collection("User").document(userID)
                .update("enPoint", newEnPoint).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("AuthRepository", "User profile's enPoint successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthRepository", "Error updating user profile's enPoint", e);
                    }
                });
        loadUserProfile(userID);
    }

    public void updateUserProfileCurrentEx(String userID, Long newCurrentEx) {
        firebaseFirestore.collection("User").document(userID)
                .update("currentEx", newCurrentEx).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("AuthRepository", "User profile's currentEx successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthRepository", "Error updating user profile's currentEx", e);
                    }
                });
        loadUserProfile(userID);
    }

    public void updateUsersGb(String userID, Long newsGb) {
        firebaseFirestore.collection("User").document(userID)
                .update("sGb", newsGb).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("AuthRepository", "User profile's small G box successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthRepository", "Error updating user profile's sGiftbox", e);
                    }
                });
        loadUserProfile(userID);
    }

    public void updateUserbGb(String userID, Long newbGb) {
        firebaseFirestore.collection("User").document(userID)
                .update("bGb", newbGb).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("AuthRepository", "User profile's small G box successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthRepository", "Error updating user profile's sGiftbox", e);
                    }
                });
        loadUserProfile(userID);
    }

    public void updateUserProfileLevel(String userID, String newLevel) {
        firebaseFirestore.collection("User").document(userID)
                .update("level", newLevel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("AuthRepository", "User profile's level successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AuthRepository", "Error updating user profile's level", e);
                    }
                });
        loadUserProfile(userID);
    }

    public interface OnUserLoad {
        void onLoad(UserModel userModel);
        void onError(Exception e);
    }
}
