import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Created by Pradeep Arya on 16/10/22
 **/
@Slf4j
public class DayInformer {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        String schema = """
                    type Query {
                        totalDays: Int!
                        allDays: [Day!]!
                    }
                    
                    type Day {
                        id: ID!
                        date: String!
                        temperature: Float!
                        season: String!
                    }
                """;
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("totalDays", new StaticDataFetcher(RANDOM.nextInt(365, 367))))
                .type("Query", builder -> builder.dataFetcher("allDays", new StaticDataFetcher(days())))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        ExecutionResult executionResult = build.execute("{totalDays}");
        log.info(executionResult.getData().toString());

        executionResult = build.execute("""
                {
                    allDays {
                        id
                        date
                        temperature
                        season
                    }
                }
                """);
        log.info(executionResult.getData().toString());

    }

    private static List<Day> days() {

        return IntStream
                .range(1, RANDOM.nextInt(2, 10))
                .mapToObj(index -> Day.builder()
                        .id(UUID.randomUUID().toString())
                        .date(LocalDateTime.now().toString())
                        .temperature(RANDOM.nextFloat())
                        .season(Ritu.values()[RANDOM.nextInt(0, 6)].name())
                        .build())
                .toList();
    }
}
