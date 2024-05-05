package org.djr4488.quarkus;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.vertx.ext.web.RoutingContext;
import org.djr4488.quarkus.controller.WeatherController;
import org.djr4488.quarkus.initiator.GlobalScheduler;
import org.djr4488.quarkus.model.AudioResponse;
import org.djr4488.quarkus.model.globe.Feature;
import org.djr4488.quarkus.model.globe.Geometry;
import org.djr4488.quarkus.model.globe.GlobeResponse;
import org.djr4488.quarkus.model.globe.Properties;
import org.djr4488.quarkus.model.onecall.OpenWeatherOneCallResponse;
import org.djr4488.quarkus.model.rss.RssResponse;
import org.djr4488.quarkus.model.store.Tile;
import org.djr4488.quarkus.model.store.Track;
import org.djr4488.quarkus.model.store.WeatherLocation;
import org.slf4j.Logger;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Path("api/2.0")
public class WeatherResource {
    @Inject
    RoutingContext context;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance weather(OpenWeatherOneCallResponse weather);
        public static native TemplateInstance weatherCurrent(OpenWeatherOneCallResponse weather);
        public static native TemplateInstance globalWeatherCurrent(OpenWeatherOneCallResponse globalWeatherCurrent);
        public static native TemplateInstance audio(AudioResponse audio);
    }

    @Inject
    WeatherController weatherController;
    @Inject
    Logger log;

    @GET
    @Path("weather/full/{zipCode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weatherFull(@PathParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weatherFull() completed with response.name:{}", response.getPlace());
        return Templates.weather(response);
    }

    @GET
    @Path("weather/full_LL/{lat}/{lon}/{altitude}/{heading}/{speed}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weatherFullLatLon(@PathParam("lat") final String lat, @PathParam("lon") final String lon,
                                              @PathParam("altitude") final String altitude, @PathParam("heading") final String heading,
                                              @PathParam("speed") final String speed) {
        final OpenWeatherOneCallResponse response = weatherController.getWeatherFullByLatLon(lat, lon, context.request().remoteAddress().toString(), altitude, heading, speed);
        log.info("weatherFullLatLong() lat:{}, lon:{}, response:{}", lat, lon, response);
        return Templates.weather(response);
    }

    @GET
    @Path("weather/full/{lat}/{lon}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherOneCallResponse weatherFull(@PathParam("lat") String lat, @PathParam("lon") String lon) {
        OpenWeatherOneCallResponse response = weatherController.getWeatherFullByLatLon(lat, lon, context.request().remoteAddress().toString(), null, null, null);
        log.info("weatherFull() lat:{}, lon:{}, response:{}", lat, lon, response);
        return response;
    }

    @GET
    @Path("audio/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance audio(@PathParam("id") int id) {
        AudioResponse response = new AudioResponse();
        switch (id) {
            case 1:
                response.setAudioSource("/audio/05_Local_Forecast_(Nighttime).mp3");
                break;
            case 2:
                response.setAudioSource("/audio/07_Regional_Forecast_(Nighttime).mp3");
                break;
            case 3:
                response.setAudioSource("/audio/09_Global_Forecast_Nighttime.mp3");
                break;
            case 4:
                response.setAudioSource("/audio/11_Global_Forecast_Nighttime_Layer_Only.mp3");
                break;
            case 5:
                response.setAudioSource("/audio/00_Museum00.mp3");
                break;
            case 6:
                response.setAudioSource("/audio/NM2_Museum00.mp3");
                break;
            case 7:
                response.setAudioSource("/audio/02_Downloading_the_Latest_News.mp3");
                break;
            case 8:
                response.setAudioSource("/audio/05_Sildeshow_Nighttime.mp3");
                break;
            case 9:
                response.setAudioSource("/audio/06_Global_News_View.mp3");
                break;
            case 10:
                response.setAudioSource("/audio/07_Globe_Layer_Only.mp3");
                break;
            case 11:
                response.setAudioSource("/audio/Legend_of_Mana.mp3");
                break;
            case 12:
                response.setAudioSource("/audio/Chrono_Cross.mp3");
                break;
            default:
                response.setAudioSource("/audio/11_Global_Forecast_Nighttime_Layer_Only.mp3");
                break;
        }
        return Templates.audio(response);
    }

    @GET
    @Path("weather")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance weather(@QueryParam("zipCode") String zipCode) {
        OpenWeatherOneCallResponse response = weatherController.getFullWeather(zipCode);
        log.info("weather() completed with response.place:{}", response.getPlace());
        return Templates.weather(response);
    }

    @GET
    @Path("globeIcon")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeWeather(@QueryParam("zipCode") String zipCode) {
        log.info("globeWeather() entered zipCode:{}", zipCode);
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = weatherController.loadMostRecentSearchedWeatherDataForLocation(zipCode);
            log.info("globeWeather() response from database:{}", response);
            List<BigDecimal> coordinates = new ArrayList<>();
            coordinates.add(response.getLon());
            coordinates.add(response.getLat());
            Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
            Geometry geometry = new Geometry("point", coordinates);
            Feature feature = new Feature(properties, geometry);
            List<Feature> features = new ArrayList<>();
            features.add(feature);
            return new GlobeResponse(response.getPlace(), response.getCurrent().getTemp().toString(), response.getLat(), response.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }

    @GET
    @Path("globeHour")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeHourWeather() {
        log.info("globeHourWeather()");
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = null;
            OpenWeatherOneCallResponse olatheResponse = null;
            List<Feature> features = new ArrayList<>();
            Set<String> locations = weatherController.loadDistinctWeatherLocations();
            for (String location : locations) {
                response = weatherController.loadMostRecentSearchedWeatherDataForLocation(location);
                if (response != null) {
                    log.info("globeWeather() response from database:{}", response);
                    List<BigDecimal> coordinates = new ArrayList<>();
                    coordinates.add(response.getLon());
                    coordinates.add(response.getLat());
                    Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
                    Geometry geometry = new Geometry("point", coordinates);
                    Feature feature = new Feature(properties, geometry);
                    features.add(feature);
                    if (response.getPlace().equalsIgnoreCase("olathe")) {
                        olatheResponse = response;
                    }
                }
            }
            return new GlobeResponse(olatheResponse.getPlace(), olatheResponse.getCurrent().getTemp().toString(), olatheResponse.getLat(), olatheResponse.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }

    @GET
    @Path("globeHour/{lat}/{lon}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse globeHourWeather(@PathParam("lat") String lat, @PathParam("lon") String lon) {
        log.info("globeHourWeather() lat:{}, lon:{}", lat, lon);
        try {
            Thread.sleep(500);
            OpenWeatherOneCallResponse response = null;
            OpenWeatherOneCallResponse olatheResponse = null;
            List<Feature> features = new ArrayList<>();
            response = weatherController.getWeatherFullByLatLon(lat, lon, context.request().remoteAddress().toString(), null, null, null);
            log.info("globeWeather() response from database:{}", response);
            List<BigDecimal> coordinates = new ArrayList<>();
            coordinates.add(response.getLon());
            coordinates.add(response.getLat());
            Properties properties = new Properties(response.getPlace(), response.getLat(), response.getLon(), response.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(response).render());
            Geometry geometry = new Geometry("point", coordinates);
            Feature feature = new Feature(properties, geometry);
            features.add(feature);
            olatheResponse = response;
            log.info("globeHourWeather() completed for lat:{}, lon:{}", lat, lon);
            return new GlobeResponse(olatheResponse.getPlace(), olatheResponse.getCurrent().getTemp().toString(), olatheResponse.getLat(), olatheResponse.getLon(), features);
        } catch (Exception ex) {
            log.error("exception:", ex);
            return null;
        }
    }

    @GET
    @Path("tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GlobeResponse tracks() {
        List<Track> tracks = weatherController.getTracksForToday();
        GlobeResponse response = new GlobeResponse();
        List<Feature> features = new ArrayList<>();
        for (Track track : tracks) {
            OpenWeatherOneCallResponse owocResp = weatherController.loadMostRecentSearchedWeatherDataForLocation(track.getWeatherLocation().getLocationName());
            Properties properties = new Properties(track.getWeatherLocation().getLocationName(),
                    new BigDecimal(track.getLat()), new BigDecimal(track.getLon()), owocResp.getCurrent().getTemp().toString(), Templates.globalWeatherCurrent(owocResp).render());
            List<BigDecimal> coordinates = new ArrayList<>();
            coordinates.add(properties.getLongitude());
            coordinates.add(properties.getLatitude());
            Geometry geometry = new Geometry("point", coordinates);
            Feature feature = new Feature(properties, geometry);
            features.add(feature);
        }
        response.setFeatures(features);
        response.setLat(features.get(0).getProperties().getLatitude());
        response.setLon(features.get(0).getProperties().getLongitude());
        response.setCity(features.get(0).getProperties().getName());
        return response;
    }

    @GET
    @Path("tracks/{s}/{x}/{y}/{z}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] tracks(@PathParam("s") final String s,
                                @PathParam("x") final int x,
                                @PathParam("y") final int y,
                                @PathParam("z") final int z)
    throws Exception {
        log.info("tracks() entered with s:{}, x:{}, y:{}, z:{}", s, x, y, z);
        // look up if we have the tile cached first
        return weatherController.getTileImageData(s, x, y, z);
    }

    //https://maps{s}.aerisapi.com/[CLIENT_ID]_[CLIENT_SECRET]/blue-marble,temperatures-text,radar,alerts,lightning-strike-density,admin-cities-dk,states,counties,interstates,roads,rivers/{z}/{x}/{y}/current.png
    @GET
    @Path("aerisWeather/{s}/{x}/{y}/{z}")
    @Produces("image/png")
    public byte[] aerisWeather(@PathParam("s") final String s,
                         @PathParam("x") final int x,
                         @PathParam("y") final int y,
                         @PathParam("z") final int z)
            throws Exception {
        log.info("tracks() entered with s:{}, x:{}, y:{}, z:{}", s, x, y, z);
        HttpClient client = HttpClient.newBuilder()
                                      .version(HttpClient.Version.HTTP_2)
                                      .followRedirects(HttpClient.Redirect.NORMAL)
                                      .build();
        HttpRequest request = HttpRequest.newBuilder()
                                         .GET()
                                         ///https://maps{s}.aerisapi.com/[CLIENT_ID]_[CLIENT_SECRET]/blue-marble,temperatures-text,radar,alerts,lightning-strike-density,admin-cities-dk,states,counties,interstates,roads,rivers/{z}/{x}/{y}/current.png
                                         .uri(URI.create("https://maps.aerisapi.com/[CLIENT_ID]_[CLIENT_SECRET]/blue-marble,temperatures-text,radar,alerts,lightning-strike-density,admin-cities-dk,states,counties,interstates,roads,rivers/"+z+"/"+x+"/"+y+"/current.png"))
                                         .setHeader("User-Agent", "Java 17 HttpClient Bot") // add request header
                                         .header("Content-Type", "image/png")
                                         .build();

        return client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
    }

    @Path("error/{error}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public void error(@PathParam("error") final String error) {
        log.error("error() from client received error:{}", error);
    }
}