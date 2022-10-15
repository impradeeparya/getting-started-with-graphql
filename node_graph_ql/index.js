// ApolloServer is a nodeJs implementation of GraphQL in node
const {
    ApolloServer,
    gql,
    MockList
} = require("apollo-server");

const typeDefs = gql`
    scalar Date
    scalar EmailId
    scalar URL
    
    """
    An object describe the characteristics of a Day
    """
    type Day {
        "Day unique identifier"
        id: ID!
        "The date of the Day"
        date: Date!
        "The temperature of the Day"
        temperature: Float! 
        "Season on which a Day fall"
        season: Ritu
        description: String!
    }
    
    enum Ritu {
        VASANT
        GREESHM
        VARSHA
        SHARAD
        HEMMANT
        SHEET   
    }
    type Query{
        totalDays: Int!
        allDays: [Day!]!
    }
    
    input AddDayInput {
        date: Date!
        temperature: Float!
        season: Ritu!
    }
    
    type RemoveDayResponse {
        day: Day!
        removed: Boolean
        totalBefore: Int
        totalAfter: Int
    }
    
    type Mutation {
        addDay(input: AddDayInput!): Day!
        removeDay(id :ID!) : Day!
        removeDayWithResponse(id: ID!): RemoveDayResponse!
    }
`;

const mocks = {
    Date: () => "12/21/2012",
    String: () => "This is a test String",
    // Query: () => ({
    //     allDays: () => new MockList(3)
    // })
}

const server = new ApolloServer({
    typeDefs,
    mocks
});

server
    .listen()
    .then(({url}) =>
        console.log(`Server running at ${url}`)
    );