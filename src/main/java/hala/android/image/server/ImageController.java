/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala.android.image.server;

/**
 *
 * @author arelin
 */
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author arelin
 */

@CrossOrigin(origins = "*") //allow this to be accessible from anywhere
@RestController
public class ImageController {
    
@RequestMapping(value = "/image-byte-array", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> getImageAsByteArray(@RequestParam(value = "ImageID") 
        String imageId, @RequestParam(value="userId") String userId) throws IOException {
    InputStream in;
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
        
    }
    return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
}


    
}
