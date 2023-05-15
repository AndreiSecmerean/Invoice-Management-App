package ro.itschool.InvoiceManagementApp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DuplicateResourceException extends Exception{
    String message;
}
