import java.util.prefs.Preferences;
import java.util.regex.Pattern;

/**
 *
 * @author Reijhanniel Jearl Campos <rj.campos@toro.io>
 */
public class LicenseManager {

    public static void main( String[] args ) {
        if ( args.length < 3 ) {
            System.out.println( "Usage: java LicenseManager [put|rem|get] <context> <varargs>" );
            System.exit( 1 );
        }

        String action = args[ 0 ].toLowerCase();
        String context = args[ 1 ];

        final int startIndex = 2;

        Preferences prefs = Preferences.userRoot().node( context );
        switch ( action ) {
            case "rem":
                for ( int i = startIndex; i < args.length; i++ ) {
                    prefs.remove( args[ i ] );
                    System.out.printf( "Removed value for: %s\n", args[ i ] );
                }
                break;
            case "get":
                for ( int i = startIndex; i < args.length; i++ )
                    System.out.printf( "%s\t=\t%s\n", args[ i ], prefs.get( args[ i ], "<none>" ) );
                break;
            case "put":
                for ( int i = startIndex; i < args.length; i++ ) {
                    String[] pair = args[ i ].split( Pattern.quote( "=" ) );
                    if ( pair.length != 2 ) {
                        System.out.printf( "Skipping: %s; Must be of form <key>=<val>\n", args[ i ] );
                        continue;
                    }
                    String value = pair[ 1 ];
                    if ( value.startsWith( "'" ) && value.endsWith( "'" )
                            || value.startsWith( "\"" ) && value.endsWith( "\"" ) )
                        value = value.substring( 1, value.length() - 1 );

                    prefs.put( pair[ 0 ], value );
                    System.out.printf( "Value for '%s' saved.\n", pair[ 0 ] );
                }
                break;
            default:
                throw new IllegalArgumentException( "Possible actions: [put|rem|get]" );
        }
    }
}
