POC for communication with Google Drive Api

**It can communicate with Google Drive Api by using service account or OAuth 2.0**

To do api calls to Google Drive you need:
- Google Account with Google Cloud Project
- Enabled Google Drive Api
- Created Service Account on Google Cloud Platform or OAuth 2.0 Client Identity

How it works:
1. The app identifies itself with the service account or gives ability to log in to user
2. The app makes a request to the Google Drive API
3. It has access to any drive with shared access (service account) or to shared drives and account drive (OAuth 2.0)

Project is based on https://developers.google.com/drive/activity/v2/quickstart/java

DEPENDENCIES:
- Drive-api - https://central.sonatype.com/artifact/com.google.apis/google-api-services-drive/versions
- Google-client-api - https://mvnrepository.com/artifact/com.google.api-client/google-api-client
- Google-oauth-client - https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client  and https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client-jetty/1.36.0

To run the app you need to provide:
GoogleOAuth2AccountKey.json - file with OAuth 2.0 client secret
GoogleServiceAccountKey.json - file with service account key