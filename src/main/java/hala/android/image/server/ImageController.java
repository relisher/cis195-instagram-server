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
import java.util.StringJoiner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.CacheControl;

/**
 *
 * @author arelin
 */

@CrossOrigin(origins = "*") //allow this to be accessible from anywhere
@RestController
public class ImageController {
    
 private MongoEntity _me = new MongoEntity();
    
@RequestMapping(value = "/get-image", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> getImage(@RequestParam(value = "imageId") 
        String imageId, @RequestParam(value="userId") String userId) throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
        HttpHeaders headers = new HttpHeaders();
        String media = _me.getImage(userId, imageId);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
     
        ResponseEntity<String> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

@RequestMapping(value = "/set-image", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> setImageAsByteArray(
        @RequestParam(value="userId") String userId, @RequestParam("image") String base64) throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
       return new ResponseEntity<String>(_me.setImage(userId, base64), HttpStatus.OK);
    }
    return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
}

@RequestMapping(value = "/list-image", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> listImages(@RequestParam(value="userId") String userId) 
        throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
        HttpHeaders headers = new HttpHeaders();
        String[] media = _me.listImages(userId);
        StringJoiner sj = new StringJoiner(",");
        for(String s : media) {
            sj.add(s);
        }
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
     
        ResponseEntity<String> responseEntity = new ResponseEntity<>(sj.toString()
                   , headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

@RequestMapping(value = "/delete-image", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> deleteImage(@RequestParam(value = "imageId") 
        String imageId, @RequestParam(value="userId") String userId) throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
        HttpHeaders headers = new HttpHeaders();
        Boolean response = _me.deleteImage(userId, imageId);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
     
        ResponseEntity<String> responseEntity = new ResponseEntity<>(response.toString()
                , headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

@RequestMapping(value = "/delete-all-images", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<?> deleteAllImage(@RequestParam(value = "imageId") 
        String imageId, @RequestParam(value="userId") String userId) throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
        HttpHeaders headers = new HttpHeaders();
        Boolean response = _me.deleteAllImages(userId);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
     
        ResponseEntity<String> responseEntity = new ResponseEntity<>(response.toString()
                , headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

    
}
