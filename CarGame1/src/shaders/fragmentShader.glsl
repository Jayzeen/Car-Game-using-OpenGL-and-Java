#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

layout(location = 0) out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

const float levels = 4.0;

void main(void)
{   
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);
   
    //TOTAL DIFFUSE AND SPECULAR LIGHT FROM 4 LIGHT SOURCES
    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);
    
    for(int i=0; i<4; i++)
    {
        //ATTENUATION LIGHT CALCULATIONs
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        
        vec3 unitLightVector = normalize(toLightVector[i]);

        //DIFFUSE LIGHTING
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        //CELL SHADING
        float level = floor(brightness * levels);
        brightness = level / levels;
        totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;

        //SPECULAR LIGHTING
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        //CELL SHADING FOR SPECULAR
        float dampedFactor = pow(specularFactor, shineDamper);
        level = floor(dampedFactor * levels);
        dampedFactor = level / levels;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity* lightColour[i]); 
    
    } 
    
    //AMBIENT LIGHT
    totalDiffuse = max(totalDiffuse, 0.5);   
        
    vec4 textureColour = texture(textureSampler, pass_textureCoords);
    if(textureColour.a < 0.5)
    {
        discard;
    }
    
    out_Color = vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular,1.0);
    out_Color = mix(vec4(skyColour,1.0), out_Color, visibility);
}