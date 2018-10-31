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
import org.springframework.web.bind.annotation.RequestBody;

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
        String description = _me.getImageDescription(userId, imageId);
        String response = "{ image_object : { image :" + media + " , description :" + description + "}}";
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
     
        ResponseEntity<String> responseEntity = new ResponseEntity<>(response, headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

@RequestMapping(value = "/set-image", method = RequestMethod.POST)
public @ResponseBody ResponseEntity<?> setImageAsByteArray(
        @RequestParam(value="userId") String userId, @RequestBody Image image) throws IOException {
    if(AuthenticatedUsers.isAuthenticatedUser(userId)) {
       String imageResponse = "{ status : " + _me.setImage(userId, image.getPicture(), image.getDescription()) + "}";
       return new ResponseEntity<String>(imageResponse, HttpStatus.OK);
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
        String images = "{ images : [" + sj.toString() + "] }";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(images
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
        String responseString = "{ success : " + response.toString() + "}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseString
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
        String responseString = "{ success : " + response.toString() + "}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseString
                , headers, HttpStatus.OK);
        return responseEntity;
    }
    return new ResponseEntity<String>("Unauthorized",  HttpStatus.UNAUTHORIZED);
}

    
}
