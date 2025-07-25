package br.edu.ifba.inf008.plugins.bookstore.model;
import java.time.LocalDate;

public class User {
   private int user_id;
   private String name;
   private String email;
   private LocalDate registered_at;
   
   public User(){}

   public User (String name,String email){
      this.name = name;
      this.email = email;
      registered_at = LocalDate.now();
     
   }

   public String getName() {
       return name;
   }

   public String getEmail() {
       return email;
   }

   public int getId(){
      return user_id;
   }

   public LocalDate getRegistedAt(){
      return registered_at;
   }

   public void  setName(String name){
      this.name = name;
   }

   public void setEmail(String email){
      this.email = email;
   }

   public void setId(int user_id){
      this.user_id = user_id;
   }

   public void setRegisteredAt(LocalDate date){
      registered_at = date;
   }
}
